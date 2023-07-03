package com.backfcdev.inventory_service.service;


import com.backfcdev.inventory_service.model.dto.BaseResponse;
import com.backfcdev.inventory_service.model.dto.OrderItemRequest;
import com.backfcdev.inventory_service.model.entities.Inventory;
import com.backfcdev.inventory_service.repository.IInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class InventoryServiceImpl implements IInventoryService {

    private final IInventoryRepository inventoryRepository;


    @Override
    public Boolean isInStock(String sku) {
        var inventory = inventoryRepository.findBySku(sku);
        return inventory.filter(value -> value.getQuantity() > 0).isPresent();
    }

    @Override
    public BaseResponse areInStock(List<OrderItemRequest> orderItems) {
        List<String> skus = orderItems.stream().map(OrderItemRequest::getSku).toList();
        List<Inventory> inventories = inventoryRepository.findBySkuIn(skus);

        List<String> errorList = orderItems.stream()
                .filter(orderItem -> inventories.stream().noneMatch(value -> value.getSku().equals(orderItem.getSku())) ||
                        inventories.stream().anyMatch(value -> value.getSku().equals(orderItem.getSku()) &&
                                value.getQuantity() < orderItem.getQuantity()))
                .map(orderItem -> "Product with sku " + orderItem.getSku() +
                        (inventories.stream().noneMatch(value -> value.getSku().equals(orderItem.getSku())) ?
                                " does not exist" : " has insufficient quantity"))
                .toList();

        return new BaseResponse(errorList.toArray(String[]::new));
    }
}
