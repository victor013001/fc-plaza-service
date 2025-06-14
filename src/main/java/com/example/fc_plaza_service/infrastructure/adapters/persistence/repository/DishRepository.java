package com.example.fc_plaza_service.infrastructure.adapters.persistence.repository;

import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.DishEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.DishProjection;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends JpaRepository<DishEntity, Long> {
  boolean existsByNameAndRestaurantId(String name, Long restaurantId);

  boolean existsByIdAndRestaurantId(Long dishId, Long restaurantId);

  @Modifying
  @Query(
      "UPDATE DishEntity d SET d.price = :price, d.description = :description WHERE d.id = :dishId")
  void updateDish(
      @Param("dishId") Long dishId,
      @Param("price") Double price,
      @Param("description") String description);

  @Modifying
  @Query("UPDATE DishEntity d SET d.active = :active WHERE d.id = :id")
  void updateActiveById(@Param("id") Long id, @Param("active") boolean active);

  @Query(
"""
SELECT
    d.id AS id,
    d.name AS name,
    d.price AS price,
    d.description AS description,
    d.imageUrl AS imageUrl,
    d.active AS active,
    d.restaurant.id AS restaurantId,
    c.name AS categoryName
FROM DishEntity d
JOIN d.category c
WHERE d.restaurant.id = :restaurantId
  AND d.active = true
ORDER BY c.id
""")
  List<DishProjection> findByRestaurantIdAndActiveTrueOrderByCategoryId(
      @Param("restaurantId") Long restaurantId, Pageable pageable);
}
