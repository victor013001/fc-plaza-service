package com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper;

import com.example.fc_plaza_service.domain.model.Restaurant;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RestaurantEntityMapper {
  @Mapping(target = "id", ignore = true)
  RestaurantEntity toEntity(Restaurant restaurant);

  Restaurant toModel(RestaurantEntity restaurant);
}
