package com.backfcdev.orders_service.mapper;

import com.backfcdev.orders_service.model.dto.OrderItemRequest;
import com.backfcdev.orders_service.model.dto.OrderItemsResponse;
import com.backfcdev.orders_service.model.dto.OrderResponse;
import com.backfcdev.orders_service.model.entities.Order;
import com.backfcdev.orders_service.model.entities.OrderItems;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderResponse convertToOrderResponse(Order order) {
        return modelMapper.map(order, OrderResponse.class);
    }

    public OrderItemsResponse convertToOrderItemsResponse(OrderItems orderItems) {
        return modelMapper.map(orderItems, OrderItemsResponse.class);
    }

    public OrderItems convertToOrderItems(OrderItemRequest  orderItemRequest) {
        return modelMapper.map(orderItemRequest, OrderItems.class);
    }

    public List<OrderResponse> convertToOrderResponses(List<Order> orders) {
        return orders.stream()
                .map(this::convertToOrderResponse)
                .toList();
    }
}
