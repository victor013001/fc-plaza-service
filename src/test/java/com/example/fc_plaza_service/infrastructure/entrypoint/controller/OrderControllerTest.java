package com.example.fc_plaza_service.infrastructure.entrypoint.controller;

import static com.example.fc_plaza_service.domain.constants.RouterConst.ORDER_BASE_PATH;
import static com.example.fc_plaza_service.domain.constants.RouterConst.RESTAURANT_BASE_PATH;
import static com.example.fc_plaza_service.domain.enums.ServerResponses.ORDER_CREATED_SUCCESSFULLY;
import static com.example.fc_plaza_service.util.data.OrderRequestData.getInvalidOrderRequest;
import static com.example.fc_plaza_service.util.data.OrderRequestData.getValidOrderRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.fc_plaza_service.application.service.OrderApplicationService;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
class OrderControllerTest {

  @InjectMocks private OrderController orderController;

  @Mock private OrderApplicationService orderApplicationService;

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
  }

  @Test
  void createOrder_Success() throws Exception {
    var restaurantId = 1L;
    var orderRequest = getValidOrderRequest();
    var requestJson = objectMapper.writeValueAsString(orderRequest);

    doNothing()
        .when(orderApplicationService)
        .createOrder(eq(restaurantId), any(OrderRequest.class));

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(RESTAURANT_BASE_PATH + "/" + restaurantId + ORDER_BASE_PATH)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.data").value(ORDER_CREATED_SUCCESSFULLY.getMessage()))
        .andExpect(jsonPath("$.error").doesNotExist());
  }

  @Test
  void createOrder_BadRequest() throws Exception {
    var restaurantId = 1L;
    var orderRequest = getInvalidOrderRequest();
    var requestJson = objectMapper.writeValueAsString(orderRequest);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(RESTAURANT_BASE_PATH + "/" + restaurantId + ORDER_BASE_PATH)
                .content(requestJson)
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }
}
