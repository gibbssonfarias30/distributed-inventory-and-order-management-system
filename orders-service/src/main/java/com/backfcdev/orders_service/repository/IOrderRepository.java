package com.backfcdev.orders_service.repository;

import com.backfcdev.orders_service.model.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Long> {
}
