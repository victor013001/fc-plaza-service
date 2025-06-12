package com.example.fc_plaza_service.domain.usecase;

import static com.example.fc_plaza_service.util.data.OrderData.getValidOrder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_plaza_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_plaza_service.domain.model.Order;
import com.example.fc_plaza_service.domain.spi.OrderPersistencePort;
import java.util.List;
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
    when(orderPersistencePort.allOrdersInDelivered(order.clientId())).thenReturn(true);

    orderUseCase.placeOrder(order);

    verify(orderPersistencePort).allOrdersInDelivered(order.clientId());
    verify(orderPersistencePort).placeOrder(order);
  }

  @Test
  void placeOrder_shouldThrowBadRequestIfNotAllDelivered() {
    var order = getValidOrder();

    when(orderPersistencePort.allOrdersInDelivered(order.clientId())).thenReturn(false);

    assertThrows(BadRequest.class, () -> orderUseCase.placeOrder(order));

    verify(orderPersistencePort).allOrdersInDelivered(order.clientId());
    verify(orderPersistencePort, never()).placeOrder(any());
  }

  @Test
  void getOrders_shouldDelegateToPersistence() {
    int page = 0;
    int size = 10;
    String sortedBy = "PENDING";
    Long currentUserId = 5L;

    List<Order> expectedOrders = List.of(getValidOrder());

    when(orderPersistencePort.getOrders(page, size, sortedBy, currentUserId))
        .thenReturn(expectedOrders);

    List<Order> result = orderUseCase.getOrders(page, size, sortedBy, currentUserId);

    assertThat(result).isEqualTo(expectedOrders);
    verify(orderPersistencePort).getOrders(page, size, sortedBy, currentUserId);
  }
}
