package com.example.fc_plaza_service.infrastructure.adapters.persistence.repository;

import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {}
