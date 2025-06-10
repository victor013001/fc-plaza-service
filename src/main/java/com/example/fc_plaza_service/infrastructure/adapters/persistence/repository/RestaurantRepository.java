package com.example.fc_plaza_service.infrastructure.adapters.persistence.repository;

import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
  boolean existsByNit(String nit);

  boolean existsByPhone(String phone);

  @Query("SELECT r.userId FROM RestaurantEntity r WHERE r.id = :restaurantId")
  Long findUserIdByRestaurantId(Long restaurantId);
}
