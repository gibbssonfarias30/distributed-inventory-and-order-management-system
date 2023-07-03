package com.backfcdev.inventory_service.service;

import com.backfcdev.inventory_service.model.dto.BaseResponse;
import com.backfcdev.inventory_service.model.dto.OrderItemRequest;

import java.util.List;

public interface IInventoryService {
    Boolean isInStock(String sku);

    BaseResponse areInStock(List<OrderItemRequest> orderItems);
}
