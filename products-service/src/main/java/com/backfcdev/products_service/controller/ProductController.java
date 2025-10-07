package com.backfcdev.products_service.controller;


import com.backfcdev.products_service.model.dto.ProductRequest;
import com.backfcdev.products_service.model.dto.ProductResponse;
import com.backfcdev.products_service.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;


@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    ResponseEntity<ProductResponse> save(@RequestBody ProductRequest request) {
        return ResponseEntity.status(CREATED)
                .body(productService.save(request));
    }
}
