package com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper;

import com.example.fc_plaza_service.domain.model.Order;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.OrderEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderEntityMapper {
  OrderEntity toEntity(Order order, RestaurantEntity restaurant);
}
