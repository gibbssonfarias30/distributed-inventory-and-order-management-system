package com.backfcdev.orders_service.events;

import com.backfcdev.orders_service.model.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
