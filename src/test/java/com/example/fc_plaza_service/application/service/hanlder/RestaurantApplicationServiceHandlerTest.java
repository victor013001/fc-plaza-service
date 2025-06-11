package com.example.fc_plaza_service.application.service.hanlder;

import static com.example.fc_plaza_service.util.data.RestaurantData.getRestaurants;
import static com.example.fc_plaza_service.util.data.RestaurantRequestData.getValidRestaurantRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_plaza_service.application.mapper.RestaurantMapper;
import com.example.fc_plaza_service.application.mapper.RestaurantMapperImpl;
import com.example.fc_plaza_service.domain.api.RestaurantServicePort;
import com.example.fc_plaza_service.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RestaurantApplicationServiceHandlerTest {
  @InjectMocks private RestaurantApplicationServiceHandler restaurantApplicationServiceHandler;

  @Spy private RestaurantMapper restaurantMapper = new RestaurantMapperImpl();

  @Mock private RestaurantServicePort restaurantService;

  @Test
  void createLandlord_ShouldEncodePasswordAndSaveRestaurant() {
    var request = getValidRestaurantRequest();

    restaurantApplicationServiceHandler.createRestaurant(request);

    verify(restaurantMapper).toModel(request);
    verify(restaurantService).saveRestaurant(any(Restaurant.class));
  }

  @Test
  void getRestaurants_ShouldReturnMappedResponses() {
    int page = 1;
    int size = 10;
    String direction = "asc";
    var restaurants = getRestaurants();

    when(restaurantService.getRestaurants(page, size, direction)).thenReturn(restaurants);

    var responses = restaurantApplicationServiceHandler.getRestaurants(page, size, direction);

    assertEquals(restaurants.size(), responses.size());
    verify(restaurantService).getRestaurants(page, size, direction);
    verify(restaurantMapper, times(3)).toResponse(any(Restaurant.class));
  }
}
