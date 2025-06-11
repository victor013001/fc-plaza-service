package com.example.fc_plaza_service.application.service.hanlder;

import static com.example.fc_plaza_service.util.data.OrderRequestData.getValidOrderRequest;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_plaza_service.application.mapper.OrderMapper;
import com.example.fc_plaza_service.application.mapper.OrderMapperImpl;
import com.example.fc_plaza_service.domain.api.OrderServicePort;
import com.example.fc_plaza_service.domain.model.Order;
import com.example.fc_plaza_service.domain.spi.UserServicePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderApplicationServiceHandlerTest {

  @InjectMocks private OrderApplicationServiceHandler orderApplicationServiceHandler;

  @Mock private OrderServicePort orderServicePort;

  @Mock private UserServicePort userServicePort;

  @Spy private OrderMapper orderMapper = new OrderMapperImpl(); // si usas MapStruct

  @Test
  void createOrder_shouldMapAndPlaceOrder() {
    Long restaurantId = 1L;
    Long clientId = 5L;
    var request = getValidOrderRequest();

    when(userServicePort.getCurrentUserId()).thenReturn(clientId);

    orderApplicationServiceHandler.createOrder(restaurantId, request);

    verify(userServicePort).getCurrentUserId();
    verify(orderMapper).toModel(restaurantId, clientId, request);
    verify(orderServicePort).placeOrder(any(Order.class));
  }
}
