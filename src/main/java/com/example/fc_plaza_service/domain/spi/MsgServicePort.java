package com.example.fc_plaza_service.domain.spi;

public interface MsgServicePort {
  void sendMessage(Long orderId, Long userId);

  boolean isValidPin(Long orderId, Integer pin);
}
