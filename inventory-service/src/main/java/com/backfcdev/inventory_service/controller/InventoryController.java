package com.backfcdev.inventory_service.controller;


import com.backfcdev.inventory_service.model.dto.BaseResponse;
import com.backfcdev.inventory_service.model.dto.OrderItemRequest;
import com.backfcdev.inventory_service.model.entities.Inventory;
import com.backfcdev.inventory_service.service.IInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final IInventoryService inventoryService;


    @GetMapping("/{sku}")
    ResponseEntity<Boolean> isInStock(@PathVariable String sku) {
        return ResponseEntity.ok(inventoryService.isInStock(sku));
    }

    @PostMapping("/in-stock")
    ResponseEntity<BaseResponse> areInStock(@RequestBody List<OrderItemRequest> orderItems) {
        return ResponseEntity.ok(inventoryService.areInStock(orderItems));
    }
}
