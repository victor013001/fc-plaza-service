package com.example.fc_plaza_service.domain.api;

public interface UserServicePort {
  boolean existsLandlordById(Long userId);

  boolean doesLandlordBelongToEmail(Long userId);
}
