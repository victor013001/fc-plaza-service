package com.example.fc_plaza_service.infrastructure.adapters.persistence.adapter;

import static com.example.fc_plaza_service.util.data.DishEntityData.getDishEntity;
import static com.example.fc_plaza_service.util.data.OrderData.getValidOrder;
import static com.example.fc_plaza_service.util.data.OrderEntityData.getOrderEntity;
import static com.example.fc_plaza_service.util.data.RestaurantEntityData.getRestaurantEntity;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_plaza_service.domain.enums.OrderStatus;
import com.example.fc_plaza_service.domain.model.Order;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.DishEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.OrderDishEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.OrderEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.entity.RestaurantEntity;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper.OrderDishEntityMapper;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper.OrderDishEntityMapperImpl;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper.OrderEntityMapper;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.mapper.OrderEntityMapperImpl;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.DishRepository;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.OrderDishRepository;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.OrderRepository;
import com.example.fc_plaza_service.infrastructure.adapters.persistence.repository.RestaurantRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class OrderPersistenceAdapterTest {

  @InjectMocks private OrderPersistenceAdapter orderPersistenceAdapter;

  @Mock private OrderRepository orderRepository;
  @Mock private OrderDishRepository orderDishRepository;
  @Mock private DishRepository dishRepository;
  @Mock private RestaurantRepository restaurantRepository;

  @Spy private OrderEntityMapper orderEntityMapper = new OrderEntityMapperImpl();

  @Spy private OrderDishEntityMapper orderDishEntityMapper = new OrderDishEntityMapperImpl();

  @Test
  void placeOrder_shouldSaveOrderAndOrderDish() {
    var order = getValidOrder();
    var restaurantEntity = getRestaurantEntity();
    var dishEntity = getDishEntity();

    when(restaurantRepository.getReferenceById(anyLong())).thenReturn(restaurantEntity);
    when(orderRepository.save(any(OrderEntity.class)))
        .thenAnswer(
            invocation -> {
              OrderEntity orderSaved = invocation.getArgument(0);
              orderSaved.setId(1L);
              return orderSaved;
            });
    when(dishRepository.getReferenceById(anyLong())).thenReturn(dishEntity);
    when(orderDishRepository.save(any(OrderDishEntity.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    orderPersistenceAdapter.placeOrder(order);

    verify(restaurantRepository).getReferenceById(anyLong());
    verify(orderRepository).save(any(OrderEntity.class));
    verify(dishRepository).getReferenceById(anyLong());
    verify(orderDishRepository).save(any(OrderDishEntity.class));
    verify(orderEntityMapper).toEntity(any(Order.class), any(RestaurantEntity.class));
    verify(orderDishEntityMapper).toEntity(any(OrderEntity.class), any(DishEntity.class), anyInt());
  }

  @Test
  void allOrdersInDelivered_shouldReturnTrueIfNoUndeliveredOrders() {
    Long clientId = 1L;
    when(orderRepository.noOrdersWithStatusDifferentFromDelivered(clientId)).thenReturn(true);

    boolean result = orderPersistenceAdapter.allOrdersInDelivered(clientId);

    assertTrue(result);
    verify(orderRepository).noOrdersWithStatusDifferentFromDelivered(clientId);
  }

  @Test
  void getOrders_shouldFetchAndMapOrders() {
    int page = 0, size = 5;
    Long chefId = 2L;
    String status = "PENDING";
    var orderEntity = getOrderEntity();
    var order = getValidOrder();
    Page<OrderEntity> orderPage = new PageImpl<>(List.of(orderEntity));

    when(orderRepository.findAllByRestaurantIdAndStatus(
            eq(chefId), eq(OrderStatus.PENDING), any(PageRequest.class)))
        .thenReturn(orderPage);
    when(orderEntityMapper.toModel(orderEntity)).thenReturn(order);

    List<Order> result = orderPersistenceAdapter.getOrders(page, size, status, chefId);

    assertThat(result).hasSize(1).contains(order);
    verify(orderRepository)
        .findAllByRestaurantIdAndStatus(
            eq(chefId), eq(OrderStatus.PENDING), any(PageRequest.class));
    verify(orderEntityMapper).toModel(orderEntity);
  }

  @Test
  void orderBelongsToRestaurant_shouldDelegateToRepository() {
    Long orderId = 1L;
    Long restaurantId = 2L;
    when(orderRepository.existsByIdAndRestaurantId(orderId, restaurantId)).thenReturn(true);

    boolean result = orderPersistenceAdapter.orderBelongsToRestaurant(orderId, restaurantId);

    assertTrue(result);
    verify(orderRepository).existsByIdAndRestaurantId(orderId, restaurantId);
  }

  @Test
  void setChefId_shouldCallRepositoryMethod() {
    Long orderId = 1L;
    Long chefId = 2L;

    orderPersistenceAdapter.setChefId(orderId, chefId);

    verify(orderRepository).assignChefAndSetInPreparation(orderId, chefId);
  }

  @Test
  void orderInPending_shouldReturnTrueWhenOrderIsPending() {
    Long orderId = 1L;
    when(orderRepository.isOrderPending(orderId)).thenReturn(true);

    boolean result = orderPersistenceAdapter.orderInPending(orderId);

    assertTrue(result);
    verify(orderRepository).isOrderPending(orderId);
  }

  @Test
  void getOrderClientId() {
    Long orderId = 1L;
    Long userId = 1L;
    when(orderRepository.getClientIdById(orderId)).thenReturn(userId);
    var clientId = orderPersistenceAdapter.getOrderUser(orderId);
    assertEquals(userId, clientId);
    verify(orderRepository).getClientIdById(orderId);
  }
}
