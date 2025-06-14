package com.example.fc_plaza_service.infrastructure.adapters.msg_service.feign;

import com.example.fc_plaza_service.domain.exceptions.StandardError;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.DefaultServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "msg-service", url = "http://localhost:8082")
public interface MsgFeignClient {
  @PostMapping("/api/v1/messaging/order/{order_id}/user/{user_id}")
  DefaultServerResponse<String, StandardError> sendMessage(
      @PathVariable(name = "order_id") Long orderId, @PathVariable(name = "user_id") Long userId);
}
