package com.backfcdev.notification_service.events;


import com.backfcdev.notification_service.enums.OrderStatus;

public record OrderEvent(String orderNumber, int itemsCount, OrderStatus orderStatus) {
}
