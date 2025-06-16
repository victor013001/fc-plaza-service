package com.example.fc_plaza_service.domain.model;

import com.example.fc_plaza_service.domain.enums.OrderStatus;
import java.time.LocalDateTime;

public record TraceOrderRequest(
    Long orderId,
    Long clientId,
    String clientEmail,
    LocalDateTime dateTime,
    OrderStatus previousStatus,
    OrderStatus newStatus,
    Long chefId,
    String chefEmail) {}
