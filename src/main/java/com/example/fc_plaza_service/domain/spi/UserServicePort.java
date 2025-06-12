package com.example.fc_plaza_service.domain.spi;

public interface UserServicePort {
  boolean existsLandlordById(Long userId);

  boolean doesLandlordBelongToEmail(Long userId);

  Long getCurrentUserId();

  Long getCurrentUserRestaurant();
}
