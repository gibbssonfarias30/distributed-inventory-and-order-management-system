package com.backfcdev.products_service.service;


import com.backfcdev.products_service.mapper.ProductMapper;
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
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductResponse> findAll() {
        return productMapper.convertToResponses(productRepository.findAll());
    }

    @Override
    public ProductResponse save(ProductRequest productRequest) {
        Product productCreated = productMapper.convertToEntity(productRequest);
        log.info("Product added: {}", productCreated);
        return productMapper.convertToResponse(productRepository.save(productCreated));
    }
}
