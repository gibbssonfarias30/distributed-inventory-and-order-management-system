package com.backfcdev.products_service.mapper;

import com.backfcdev.products_service.model.dto.ProductRequest;
import com.backfcdev.products_service.model.dto.ProductResponse;
import com.backfcdev.products_service.model.entities.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@RequiredArgsConstructor
@Configuration
public class ProductMapper {

    private final ModelMapper modelMapper;

    public Product convertToEntity(ProductRequest productRequest) {
        return modelMapper.map(productRequest, Product.class);
    }

    public ProductResponse convertToResponse(Product entity) {
        return modelMapper.map(entity, ProductResponse.class);
    }

    public List<ProductResponse> convertToResponses(List<Product> entities) {
        return entities.stream()
                .map(this::convertToResponse)
                .toList();
    }
}
