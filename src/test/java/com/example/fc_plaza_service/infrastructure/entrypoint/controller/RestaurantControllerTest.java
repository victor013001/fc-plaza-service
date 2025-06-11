package com.example.fc_plaza_service.infrastructure.entrypoint.controller;

import static com.example.fc_plaza_service.domain.constants.RouterConst.RESTAURANT_BASE_PATH;
import static com.example.fc_plaza_service.domain.enums.ServerResponses.RESTAURANT_CREATED_SUCCESSFULLY;
import static com.example.fc_plaza_service.util.data.RestaurantRequestData.getInvalidRestaurantRequest;
import static com.example.fc_plaza_service.util.data.RestaurantRequestData.getValidRestaurantRequest;
import static com.example.fc_plaza_service.util.data.RestaurantResponseData.getRestaurantResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.fc_plaza_service.application.service.RestaurantApplicationService;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.RestaurantRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class RestaurantControllerTest {

  @InjectMocks private RestaurantController restaurantController;

  @Mock private RestaurantApplicationService restaurantApplicationService;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
    objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
  }

  @Test
  public void createLandlord_Success() throws Exception {
    var restaurantRequest = getValidRestaurantRequest();

    String requestJson = objectMapper.writeValueAsString(restaurantRequest);

    Mockito.doNothing()
        .when(restaurantApplicationService)
        .createRestaurant(any(RestaurantRequest.class));

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(RESTAURANT_BASE_PATH)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data").value(RESTAURANT_CREATED_SUCCESSFULLY.getMessage()))
        .andExpect(jsonPath("$.error").doesNotExist());
  }

  @Test
  public void createLandlord_BadRequest() throws Exception {
    var restaurantRequest = getInvalidRestaurantRequest();

    String requestJson = objectMapper.writeValueAsString(restaurantRequest);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(RESTAURANT_BASE_PATH)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getRestaurants_Success() throws Exception {
    var restaurantResponse = getRestaurantResponse();

    when(restaurantApplicationService.getRestaurants(0, 10, "ASC"))
        .thenReturn(List.of(restaurantResponse));

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(RESTAURANT_BASE_PATH)
                .param("page", "0")
                .param("size", "10")
                .param("direction", "ASC")
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data").isArray())
        .andExpect(jsonPath("$.data[0].name").value(restaurantResponse.name()))
        .andExpect(jsonPath("$.error").doesNotExist());

    verify(restaurantApplicationService).getRestaurants(0, 10, "ASC");
  }
}
