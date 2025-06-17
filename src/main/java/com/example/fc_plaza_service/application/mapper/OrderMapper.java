package com.example.fc_plaza_service.application.mapper;

import com.example.fc_plaza_service.application.dto.request.OrderRequest;
import com.example.fc_plaza_service.application.dto.response.OrderResponse;
import com.example.fc_plaza_service.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "date", expression = "java(java.time.LocalDate.now())")
  @Mapping(target = "status", constant = "PENDING")
  Order toModel(Long restaurantId, Long clientId, OrderRequest orderRequest);

  OrderResponse toResponse(Order order);
}
