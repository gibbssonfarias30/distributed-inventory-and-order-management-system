package com.backfcdev.orders_service.controller;


import com.backfcdev.orders_service.model.dto.OrderRequest;
import com.backfcdev.orders_service.model.dto.OrderResponse;
import com.backfcdev.orders_service.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final IOrderService orderService;


    @GetMapping
    ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping
    ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return ResponseEntity.status(CREATED).body("Order placed successfully!");
    }
}
