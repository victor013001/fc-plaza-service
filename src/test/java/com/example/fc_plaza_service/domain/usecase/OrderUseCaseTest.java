package com.example.fc_plaza_service.domain.usecase;

import static com.example.fc_plaza_service.util.data.OrderData.getValidOrder;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.example.fc_plaza_service.domain.enums.OrderStatus;
import com.example.fc_plaza_service.domain.exceptions.standard_exception.BadRequest;
import com.example.fc_plaza_service.domain.model.Order;
import com.example.fc_plaza_service.domain.spi.MsgServicePort;
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

  @Mock private MsgServicePort msgServicePort;

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
    verifyNoInteractions(msgServicePort);
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
    verifyNoInteractions(msgServicePort);
  }

  @Test
  void changeStatus_ShouldSendPinCode() {
    Long orderId = 1L;
    Long chefId = 2L;
    OrderStatus orderStatus = OrderStatus.READY;
    Long userId = 1L;

    when(orderPersistencePort.isOrderChef(orderId, chefId)).thenReturn(true);
    when(orderPersistencePort.getOrderStatus(orderId)).thenReturn(OrderStatus.IN_PREPARATION);
    when(orderPersistencePort.getOrderUser(orderId)).thenReturn(userId);
    doNothing().when(msgServicePort).sendMessage(anyLong(), anyLong());

    orderUseCase.changeStatus(orderId, orderStatus, chefId, null);

    verify(orderPersistencePort).isOrderChef(orderId, chefId);
    verify(orderPersistencePort).getOrderStatus(orderId);
    verify(orderPersistencePort).changeStatus(orderId, orderStatus);
    verify(orderPersistencePort).getOrderUser(orderId);
    verify(msgServicePort).sendMessage(orderId, userId);
  }

  @Test
  void changeStatusToDelivered() {
    Long orderId = 1L;
    Long chefId = 2L;
    OrderStatus orderStatus = OrderStatus.DELIVERED;
    Long userId = 1L;
    Integer pin = 1234;

    when(orderPersistencePort.isOrderChef(orderId, chefId)).thenReturn(true);
    when(orderPersistencePort.getOrderStatus(orderId)).thenReturn(OrderStatus.READY);
    when(msgServicePort.isValidPin(orderId, pin)).thenReturn(true);

    orderUseCase.changeStatus(orderId, orderStatus, chefId, pin);

    verify(orderPersistencePort).isOrderChef(orderId, chefId);
    verify(orderPersistencePort).getOrderStatus(orderId);
    verify(orderPersistencePort).changeStatus(orderId, orderStatus);
    verify(msgServicePort).isValidPin(orderId, pin);
  }
}
