package com.example.fc_plaza_service.domain.model;

import com.example.fc_plaza_service.domain.enums.OrderStatus;
import java.time.LocalDate;
import java.util.List;

public record Order(
    Long clientId,
    LocalDate date,
    OrderStatus status,
    Long chefId,
    Long restaurantId,
    List<OrderDish> dishes) {}
