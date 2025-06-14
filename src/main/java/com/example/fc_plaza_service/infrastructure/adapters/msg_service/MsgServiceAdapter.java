package com.example.fc_plaza_service.infrastructure.adapters.msg_service;

import com.example.fc_plaza_service.domain.spi.MsgServicePort;
import com.example.fc_plaza_service.infrastructure.adapters.msg_service.feign.MsgFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MsgServiceAdapter implements MsgServicePort {
  private static final String LOG_PREFIX = "[MSG_SERVICE_ADAPTER] >>> ";

  private final MsgFeignClient userFeignClient;

  @Override
  public void sendMessage(Long orderId, Long userId) {
    log.info("{} Sending user: {} pin message for order: {}.", LOG_PREFIX, userId, orderId);
    userFeignClient.sendMessage(orderId, userId);
  }
}
