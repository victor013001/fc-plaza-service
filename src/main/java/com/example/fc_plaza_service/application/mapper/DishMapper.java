package com.example.fc_plaza_service.application.mapper;

import com.example.fc_plaza_service.domain.model.Dish;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.DishRequest;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.DishUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DishMapper {

  Dish toModel(Long restaurantId, DishRequest dishRequest);

  Dish toModel(DishUpdateRequest request, Long restaurantId);
}
