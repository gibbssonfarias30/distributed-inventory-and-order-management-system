package com.backfcdev.orders_service.mapper;

import com.backfcdev.orders_service.model.dto.OrderItemRequest;
import com.backfcdev.orders_service.model.dto.OrderItemsResponse;
import com.backfcdev.orders_service.model.dto.OrderResponse;
import com.backfcdev.orders_service.model.entities.Order;
import com.backfcdev.orders_service.model.entities.OrderItems;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderResponse convertToOrderResponse(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                convertToOrderItemsResponses(order.getOrderItems())
        );
    }

    public OrderItemsResponse convertToOrderItemsResponse(OrderItems orderItems) {
        return new OrderItemsResponse(
                orderItems.getId(),
                orderItems.getSku(),
                orderItems.getPrice(),
                orderItems.getQuantity()
        );
    }

    private List<OrderItemsResponse> convertToOrderItemsResponses(List<OrderItems> items) {
        return items.stream()
                .map(this::convertToOrderItemsResponse)
                .toList();
    }

    public OrderItems convertToOrderItems(OrderItemRequest orderItemRequest, Order order) {
        return OrderItems.builder()
                .id(orderItemRequest.getId())
                .sku(orderItemRequest.getSku())
                .price(orderItemRequest.getPrice())
                .quantity(orderItemRequest.getQuantity())
                .order(order)
                .build();
    }

    public List<OrderResponse> convertToOrderResponses(List<Order> orders) {
        return orders.stream()
                .map(this::convertToOrderResponse)
                .toList();
    }
}
