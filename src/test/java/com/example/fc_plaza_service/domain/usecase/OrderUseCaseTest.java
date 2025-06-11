package com.example.fc_plaza_service.domain.usecase;

import static com.example.fc_plaza_service.util.data.OrderData.getValidOrder;
import static org.mockito.Mockito.verify;

import com.example.fc_plaza_service.domain.spi.OrderPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

  @InjectMocks private OrderUseCase orderUseCase;

  @Mock private OrderPersistencePort orderPersistencePort;

  @Test
  void placeOrder_shouldCallPersistence() {
    var order = getValidOrder();

    orderUseCase.placeOrder(order);

    verify(orderPersistencePort).placeOrder(order);
  }
}
