package com.example.fc_plaza_service.infrastructure.adapters.persistence.adapter;

import static com.example.fc_plaza_service.util.data.DishEntityData.getDishEntity;
import static com.example.fc_plaza_service.util.data.OrderData.getValidOrder;
import static com.example.fc_plaza_service.util.data.RestaurantEntityData.getRestaurantEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
