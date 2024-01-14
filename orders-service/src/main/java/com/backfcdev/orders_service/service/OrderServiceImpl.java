package com.backfcdev.orders_service.service;


import com.backfcdev.orders_service.events.OrderEvent;
import com.backfcdev.orders_service.model.dto.*;
import com.backfcdev.orders_service.model.entities.Order;
import com.backfcdev.orders_service.model.entities.OrderItems;
import com.backfcdev.orders_service.model.enums.OrderStatus;
import com.backfcdev.orders_service.repository.IOrderRepository;
import com.backfcdev.orders_service.utils.JsonUtils;
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


    @Override
    public OrderResponse placeOrder(OrderRequest orderRequest) {
        //TODO: Check for inventory
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
                .map(orderItemRequest -> mapOrderItemRequestToOrderItem(orderItemRequest, order))
                .toList());

        // TODO: Send message to order topic
        this.kafkaTemplate.send("orders-topic", JsonUtils.toJson(
                new OrderEvent(order.getOrderNumber(), order.getOrderItems().size(), OrderStatus.PLACED)
        ));

        return mapToOrderResponse(this.orderRepository.save(order));
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = this.orderRepository.findAll();
        return orders.stream().map(this::mapToOrderResponse).toList();
    }



    private OrderResponse mapToOrderResponse(Order order) {
        return new OrderResponse(order.getId(), order.getOrderNumber(), order.getOrderItems().stream().map(this::mapToOrderItemRequest).toList());
    }

    private OrderItemsResponse mapToOrderItemRequest(OrderItems orderItems) {
        return new OrderItemsResponse(orderItems.getId(), orderItems.getSku(), orderItems.getPrice(), orderItems.getQuantity());
    }

    private OrderItems mapOrderItemRequestToOrderItem(OrderItemRequest orderItemRequest, Order order) {
        return OrderItems.builder()
                .id(orderItemRequest.getId())
                .sku(orderItemRequest.getSku())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .order(order)
                .build();
    }
}
