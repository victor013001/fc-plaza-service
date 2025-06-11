package com.example.fc_plaza_service.infrastructure.adapters.persistence.repository;

import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.OrderDishEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.OrderDishId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDishRepository extends JpaRepository<OrderDishEntity, OrderDishId> {}
