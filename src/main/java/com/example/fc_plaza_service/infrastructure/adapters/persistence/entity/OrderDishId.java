package com.example.fc_plaza_service.infrastructure.adapters.persistence.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public record OrderDishId(Long orderId, Long dishId) implements Serializable {}
