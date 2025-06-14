package com.example.fc_plaza_service.infrastructure.adapters.persistence.repository;

import com.example.fc_plaza_service.domain.enums.OrderStatus;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

  @Query(
"""
    SELECT COUNT(o) = 0
    FROM OrderEntity o
    WHERE o.clientId = :clientId AND o.status <> 'DELIVERED'
""")
  boolean noOrdersWithStatusDifferentFromDelivered(@Param("clientId") Long clientId);

  Page<OrderEntity> findAllByRestaurantIdAndStatus(
      Long restaurantId, OrderStatus status, Pageable pageable);

  boolean existsByIdAndRestaurantId(Long orderId, Long restaurantId);

  @Modifying
  @Query(
      "UPDATE OrderEntity o SET o.chefId = :chefId, o.status = 'IN_PREPARATION' WHERE o.id = :orderId")
  void assignChefAndSetInPreparation(@Param("orderId") Long orderId, @Param("chefId") Long chefId);

  @Query("SELECT COUNT(o) > 0 FROM OrderEntity o WHERE o.id = :orderId AND o.status = 'PENDING'")
  boolean isOrderPending(@Param("orderId") Long orderId);

  boolean existsByIdAndChefId(Long orderId, Long chefId);

  OrderStatus getStatusById(Long orderId);

  Long getClientIdById(Long orderId);

  boolean existsByIdAndClientId(Long orderId, Long currentUserId);
}
