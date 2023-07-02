package com.backfcdev.products_service.repository;

import com.backfcdev.products_service.model.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> {
}
