package com.example.fc_plaza_service.infrastructure.adapters.persistence.repository;

import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<DishEntity, Long> {
  boolean existsByNameAndRestaurantId(String name, Long restaurantId);
}
