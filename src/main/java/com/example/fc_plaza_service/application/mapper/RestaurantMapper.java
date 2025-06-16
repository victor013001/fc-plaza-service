package com.example.fc_plaza_service.application.mapper;

import com.example.fc_plaza_service.application.dto.request.RestaurantRequest;
import com.example.fc_plaza_service.application.dto.response.RestaurantResponse;
import com.example.fc_plaza_service.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RestaurantMapper {
  Restaurant toModel(RestaurantRequest restaurantRequest);

  RestaurantResponse toResponse(Restaurant restaurant);
}
