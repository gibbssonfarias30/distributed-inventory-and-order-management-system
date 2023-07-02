package com.backfcdev.products_service.service;

import com.backfcdev.products_service.model.dto.ProductRequest;
import com.backfcdev.products_service.model.dto.ProductResponse;

import java.util.List;

public interface IProductService {
    ProductResponse addProduct(ProductRequest productRequest);

    List<ProductResponse> getAllProducts();
}
