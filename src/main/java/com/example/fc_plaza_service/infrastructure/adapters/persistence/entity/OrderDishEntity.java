package com.example.fc_plaza_service.infrastructure.adapters.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_dish")
public class OrderDishEntity {

  @EmbeddedId private OrderDishId id;

  @MapsId("orderId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private OrderEntity order;

  @MapsId("dishId")
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "dish_id")
  private DishEntity dish;

  @Column private Integer quantity;
}
