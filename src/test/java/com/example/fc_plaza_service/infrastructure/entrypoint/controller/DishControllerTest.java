package com.example.fc_plaza_service.infrastructure.entrypoint.controller;

import static com.example.fc_plaza_service.domain.constants.RouterConst.DISH_BASE_PATH;
import static com.example.fc_plaza_service.domain.constants.RouterConst.ENABLE_BASE_PATH;
import static com.example.fc_plaza_service.domain.constants.RouterConst.MENU_BASE_PATH;
import static com.example.fc_plaza_service.domain.constants.RouterConst.RESTAURANT_BASE_PATH;
import static com.example.fc_plaza_service.domain.enums.ServerResponses.DISH_CREATED_SUCCESSFULLY;
import static com.example.fc_plaza_service.domain.enums.ServerResponses.DISH_UPDATED_SUCCESSFULLY;
import static com.example.fc_plaza_service.util.data.DishRequestData.getInvalidDishRequest;
import static com.example.fc_plaza_service.util.data.DishRequestData.getInvalidDishUpdateRequest;
import static com.example.fc_plaza_service.util.data.DishRequestData.getValidDishRequest;
import static com.example.fc_plaza_service.util.data.DishRequestData.getValidDishUpdateRequest;
import static com.example.fc_plaza_service.util.data.DishResponseData.getValidDishResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.fc_plaza_service.application.service.DishApplicationService;
import com.example.fc_plaza_service.domain.enums.ProductStatus;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.DishRequest;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.DishUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class DishControllerTest {

  @InjectMocks private DishController dishController;

  @Mock private DishApplicationService dishApplicationService;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(dishController).build();
    objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
  }

  @Test
  void createDish_Success() throws Exception {
    Long restaurantId = 1L;
    var dishRequest = getValidDishRequest();
    String requestJson = objectMapper.writeValueAsString(dishRequest);

    doNothing().when(dishApplicationService).createDish(eq(restaurantId), any(DishRequest.class));

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(RESTAURANT_BASE_PATH + "/" + restaurantId + DISH_BASE_PATH)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data").value(DISH_CREATED_SUCCESSFULLY.getMessage()))
        .andExpect(jsonPath("$.error").doesNotExist());
  }

  @Test
  void createDish_BadRequest() throws Exception {
    Long restaurantId = 1L;
    var dishRequest = getInvalidDishRequest();
    String requestJson = objectMapper.writeValueAsString(dishRequest);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(RESTAURANT_BASE_PATH + "/" + restaurantId + DISH_BASE_PATH)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void updateDish_Success() throws Exception {
    Long restaurantId = 1L;
    Long dishId = 2L;
    var updateRequest = getValidDishUpdateRequest();
    String requestJson = objectMapper.writeValueAsString(updateRequest);

    doNothing()
        .when(dishApplicationService)
        .updateDish(eq(restaurantId), eq(dishId), any(DishUpdateRequest.class));

    mockMvc
        .perform(
            MockMvcRequestBuilders.patch(
                    RESTAURANT_BASE_PATH + "/" + restaurantId + DISH_BASE_PATH + "/" + dishId)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").value(DISH_UPDATED_SUCCESSFULLY.getMessage()))
        .andExpect(jsonPath("$.error").doesNotExist());
  }

  @Test
  void updateDish_BadRequest() throws Exception {
    Long restaurantId = 1L;
    Long dishId = 2L;
    var updateRequest = getInvalidDishUpdateRequest();
    String requestJson = objectMapper.writeValueAsString(updateRequest);

    mockMvc
        .perform(
            MockMvcRequestBuilders.patch(
                    RESTAURANT_BASE_PATH + "/" + restaurantId + DISH_BASE_PATH + "/" + dishId)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void enableDish_Success() throws Exception {
    Long restaurantId = 1L;
    Long dishId = 2L;
    ProductStatus status = ProductStatus.DISABLED;

    doNothing().when(dishApplicationService).updateStatusDish(restaurantId, dishId, status);

    mockMvc
        .perform(
            MockMvcRequestBuilders.patch(
                    RESTAURANT_BASE_PATH
                        + "/"
                        + restaurantId
                        + DISH_BASE_PATH
                        + "/"
                        + dishId
                        + ENABLE_BASE_PATH)
                .queryParam("status", status.name())
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").value(DISH_UPDATED_SUCCESSFULLY.getMessage()))
        .andExpect(jsonPath("$.error").doesNotExist());
  }

  @Test
  void getRestaurantMenu_Success() throws Exception {
    var restaurantId = 1L;
    int page = 0;
    int size = 10;
    var menu = List.of(getValidDishResponse());

    when(dishApplicationService.getRestaurantMenu(restaurantId, page, size)).thenReturn(menu);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(RESTAURANT_BASE_PATH + "/" + restaurantId + MENU_BASE_PATH)
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").isArray())
        .andExpect(jsonPath("$.data[0].name").value(menu.get(0).name()))
        .andExpect(jsonPath("$.error").doesNotExist());
  }
}
