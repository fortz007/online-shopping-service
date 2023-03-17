package com.fortuneprogramming.inventoryservice.repositories;

import com.fortuneprogramming.inventoryservice.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}