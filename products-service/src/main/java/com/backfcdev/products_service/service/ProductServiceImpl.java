package com.backfcdev.products_service.service;


import com.backfcdev.products_service.model.dto.ProductRequest;
import com.backfcdev.products_service.model.dto.ProductResponse;
import com.backfcdev.products_service.model.entities.Product;
import com.backfcdev.products_service.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements IProductService{

    private final IProductRepository productRepository;


    @Override
    public ProductResponse addProduct(ProductRequest productRequest) {
        var product = Product.builder()
                .sku(productRequest.getSku())
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .status(productRequest.getStatus())
                .build();

        log.info("Product added: {}", product);
        return convertToResponse(productRepository.save(product));
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }


    private ProductResponse convertToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .sku(product.getSku())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .status(product.getStatus())
                .build();
    }
}
