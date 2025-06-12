package com.example.fc_plaza_service.infrastructure.entrypoint.dto.response;

import com.example.fc_plaza_service.domain.enums.OrderStatus;
import java.time.LocalDate;
import java.util.List;

public record OrderResponse(
    Long id,
    Long clientId,
    LocalDate date,
    OrderStatus status,
    Long chefId,
    Long restaurantId,
    List<OrderDishResponse> dishes) {}
