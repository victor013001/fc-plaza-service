package com.example.fc_plaza_service.domain.usecase;

import static com.example.fc_plaza_service.util.data.OrderData.getValidOrder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_plaza_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_plaza_service.domain.model.Order;
import com.example.fc_plaza_service.domain.spi.OrderPersistencePort;
import com.example.fc_plaza_service.domain.spi.UserServicePort;
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

  @Mock private UserServicePort userServicePort;

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
    Long restaurantId = 1L;

    List<Order> expectedOrders = List.of(getValidOrder());

    when(orderPersistencePort.getOrders(page, size, sortedBy, restaurantId))
        .thenReturn(expectedOrders);
    when(userServicePort.getCurrentUserRestaurant()).thenReturn(restaurantId);

    List<Order> result = orderUseCase.getOrders(page, size, sortedBy, currentUserId);

    assertThat(result).isEqualTo(expectedOrders);
    verify(orderPersistencePort).getOrders(anyInt(), anyInt(), anyString(), anyLong());
    verify(userServicePort).getCurrentUserRestaurant();
  }

  @Test
  void assignOrderToChef_shouldAssignWhenValid() {
    Long orderId = 1L;
    Long chefId = 2L;
    Long restaurantId = 3L;

    when(orderPersistencePort.orderBelongsToRestaurant(orderId, restaurantId)).thenReturn(true);
    when(orderPersistencePort.orderInPending(orderId)).thenReturn(true);

    orderUseCase.assignOrderToChef(orderId, chefId, restaurantId);

    verify(orderPersistencePort).orderBelongsToRestaurant(orderId, restaurantId);
    verify(orderPersistencePort).orderInPending(orderId);
    verify(orderPersistencePort).setChefId(orderId, chefId);
  }

  @Test
  void assignOrderToChef_shouldThrowIfOrderDoesNotBelongToRestaurant() {
    Long orderId = 1L;
    Long chefId = 2L;
    Long restaurantId = 3L;

    when(orderPersistencePort.orderBelongsToRestaurant(orderId, restaurantId)).thenReturn(false);

    assertThrows(
        BadRequest.class, () -> orderUseCase.assignOrderToChef(orderId, chefId, restaurantId));

    verify(orderPersistencePort).orderBelongsToRestaurant(orderId, restaurantId);
    verify(orderPersistencePort, never()).orderInPending(any());
    verify(orderPersistencePort, never()).setChefId(any(), any());
  }

  @Test
  void assignOrderToChef_shouldThrowIfOrderIsNotPending() {
    Long orderId = 1L;
    Long chefId = 2L;
    Long restaurantId = 3L;

    when(orderPersistencePort.orderBelongsToRestaurant(orderId, restaurantId)).thenReturn(true);
    when(orderPersistencePort.orderInPending(orderId)).thenReturn(false);

    assertThrows(
        BadRequest.class, () -> orderUseCase.assignOrderToChef(orderId, chefId, restaurantId));

    verify(orderPersistencePort).orderBelongsToRestaurant(orderId, restaurantId);
    verify(orderPersistencePort).orderInPending(orderId);
    verify(orderPersistencePort, never()).setChefId(any(), any());
  }
}
