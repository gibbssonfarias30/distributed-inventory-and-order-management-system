package com.backfcdev.orders_service.service;


import com.backfcdev.orders_service.events.OrderEvent;
import com.backfcdev.orders_service.mapper.OrderMapper;
import com.backfcdev.orders_service.model.dto.*;
import com.backfcdev.orders_service.model.entities.Order;
import com.backfcdev.orders_service.model.enums.OrderStatus;
import com.backfcdev.orders_service.repository.IOrderRepository;
import com.backfcdev.orders_service.utils.JsonUtils;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements IOrderService {

    private final IOrderRepository orderRepository;
    private final WebClient.Builder webClient;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObservationRegistry observationRegistry;
    private final OrderMapper orderMapper;


    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {

        Observation inventoryObservation = Observation.createNotStarted("inventory-service", observationRegistry);

        return inventoryObservation.observe(() -> {
            // Check for inventory
            BaseResponse result = this.webClient.build()
                    .post()
                    .uri("lb://inventory-service/api/v1/inventory/in-stock")
                    .bodyValue(orderRequest.getOrderItems())
                    .retrieve()
                    .bodyToMono(BaseResponse.class)
                    .block();

            Optional.ofNullable(result)
                    .filter(r -> !r.hasErrors())
                    .orElseThrow(() -> new IllegalArgumentException("Some of the products are not in stock"));

            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setOrderItems(orderRequest.getOrderItems()
                    .stream()
                    .map(orderItemRequest -> orderMapper.convertToOrderItems(orderItemRequest, order))
                    .toList());

            // Send message to order topic
            this.kafkaTemplate.send("orders-topic", JsonUtils.toJson(
                    new OrderEvent(order.getOrderNumber(), order.getOrderItems().size(), OrderStatus.PLACED)
            ));

            return orderMapper.convertToOrderResponse(orderRepository.save(order));
        });

    }

    @Override
    public List<OrderResponse> findAll() {
        return orderMapper.convertToOrderResponses(orderRepository.findAll());
    }
}
