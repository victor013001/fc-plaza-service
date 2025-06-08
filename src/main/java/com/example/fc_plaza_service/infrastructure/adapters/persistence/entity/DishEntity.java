package com.example.fc_plaza_service.infrastructure.adapters.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dish")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String name;

  @Column private Double price;

  @Column private String description;

  @Column(name = "image_url")
  private String imageUrl;

  @Column private Boolean active;

  @ManyToOne
  @JoinColumn(name = "restaurant_id")
  private RestaurantEntity restaurant;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private CategoryEntity category;
}
