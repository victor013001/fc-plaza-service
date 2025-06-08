package com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper;

import com.example.fc_plaza_service.domain.model.Dish;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DishEntityMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "active", constant = "true")
  @Mapping(target = "restaurant", ignore = true)
  @Mapping(target = "category", ignore = true)
  DishEntity toEntity(Dish dish);
}
