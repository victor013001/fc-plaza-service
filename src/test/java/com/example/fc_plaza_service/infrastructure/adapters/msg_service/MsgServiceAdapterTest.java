package com.example.fc_plaza_service.infrastructure.adapters.msg_service;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.fc_plaza_service.infrastructure.adapters.msg_service.feign.MsgFeignClient;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.DefaultServerResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MsgServiceAdapterTest {

  @InjectMocks private MsgServiceAdapter msgServiceAdapter;

  @Mock private MsgFeignClient msgFeignClient;

  @Test
  void sendPinMessage() {
    var userId = 1L;
    var orderId = 1L;

    when(msgFeignClient.sendMessage(anyLong(), anyLong()))
        .thenReturn(new DefaultServerResponse<>(null, null));
    msgServiceAdapter.sendMessage(orderId, userId);
    verify(msgFeignClient).sendMessage(orderId, userId);
  }

  @Test
  void validPin() {
    var orderId = 1L;
    var pin = 1234;

    when(msgFeignClient.validPin(anyLong(), anyInt()))
        .thenReturn(new DefaultServerResponse<>(true, null));
    msgServiceAdapter.isValidPin(orderId, pin);
    verify(msgFeignClient).validPin(orderId, pin);
  }
}
