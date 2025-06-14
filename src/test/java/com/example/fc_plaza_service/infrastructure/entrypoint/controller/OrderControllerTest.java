package com.example.fc_plaza_service.infrastructure.entrypoint.controller;

import static com.example.fc_plaza_service.domain.constants.RouterConst.ORDER_BASE_PATH;
import static com.example.fc_plaza_service.domain.constants.RouterConst.RESTAURANT_BASE_PATH;
import static com.example.fc_plaza_service.domain.enums.ServerResponses.ORDER_CREATED_SUCCESSFULLY;
import static com.example.fc_plaza_service.util.data.OrderRequestData.getInvalidOrderRequest;
import static com.example.fc_plaza_service.util.data.OrderRequestData.getValidOrderRequest;
import static com.example.fc_plaza_service.util.data.OrderResponseData.getValidOrderResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.fc_plaza_service.application.service.OrderApplicationService;
import com.example.fc_plaza_service.domain.enums.OrderStatus;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.OrderRequest;
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

  @Test
  void getRestaurants_Success() throws Exception {
    int page = 0;
    int size = 10;
    String sortBy = "PENDING";
    var orders = List.of(getValidOrderResponse());

    when(orderApplicationService.getOrders(page, size, sortBy)).thenReturn(orders);

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(RESTAURANT_BASE_PATH + ORDER_BASE_PATH)
                .param("page", String.valueOf(page))
                .param("size", String.valueOf(size))
                .param("sortBy", sortBy))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").isArray())
        .andExpect(jsonPath("$.error").doesNotExist());
  }

  @Test
  void updateOrder_Success() throws Exception {
    Long orderId = 123L;

    doNothing().when(orderApplicationService).updateOrder(anyLong(), any(OrderStatus.class), any());

    mockMvc
        .perform(
            MockMvcRequestBuilders.patch(RESTAURANT_BASE_PATH + ORDER_BASE_PATH + "/" + orderId))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").value(""))
        .andExpect(jsonPath("$.error").doesNotExist());

    verify(orderApplicationService).updateOrder(anyLong(), any(OrderStatus.class), any());
  }

  @Test
  void deliverOrder_Success() throws Exception {
    Long orderId = 123L;
    String pin = "1234";

    doNothing()
        .when(orderApplicationService)
        .updateOrder(anyLong(), any(OrderStatus.class), anyInt());

    mockMvc
        .perform(
            MockMvcRequestBuilders.patch(RESTAURANT_BASE_PATH + ORDER_BASE_PATH + "/" + orderId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(pin))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").value(""))
        .andExpect(jsonPath("$.error").doesNotExist());

    verify(orderApplicationService).updateOrder(anyLong(), any(OrderStatus.class), anyInt());
  }
}
