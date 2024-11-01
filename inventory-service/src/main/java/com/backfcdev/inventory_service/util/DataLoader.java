package com.backfcdev.inventory_service.util;

import com.backfcdev.inventory_service.model.entities.Inventory;
import com.backfcdev.inventory_service.repository.IInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataLoader  {

    private final IInventoryRepository inventoryRepository;

    @Bean
    CommandLineRunner init() {
        return args -> {
            log.info("Loading data...");
            if(inventoryRepository.findAll().isEmpty()){
                inventoryRepository.saveAll(
                        List.of(
                                Inventory.builder().sku("00001").quantity(10L).build(),
                                Inventory.builder().sku("00002").quantity(20L).build(),
                                Inventory.builder().sku("00003").quantity(30L).build(),
                                Inventory.builder().sku("00004").quantity(0L).build()
                        )
                );
            }
            log.info("data loaded...");
        };
    }
}
