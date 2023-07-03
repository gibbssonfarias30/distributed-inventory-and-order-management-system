package com.backfcdev.orders_service.service;

import com.backfcdev.orders_service.model.dto.OrderRequest;
import com.backfcdev.orders_service.model.dto.OrderResponse;

import java.util.List;

public interface IOrderService {
    void placeOrder(OrderRequest orderRequest);

    List<OrderResponse> getAllOrders();
}
