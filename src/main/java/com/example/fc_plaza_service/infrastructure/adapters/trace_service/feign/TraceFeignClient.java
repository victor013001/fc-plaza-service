package com.example.fc_plaza_service.infrastructure.adapters.trace_service.feign;

import com.example.fc_plaza_service.application.dto.request.DefaultServerResponse;
import com.example.fc_plaza_service.domain.exceptions.StandardError;
import com.example.fc_plaza_service.domain.model.TraceOrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "trace-service", url = "http://localhost:8083")
public interface TraceFeignClient {
  @PostMapping("/api/v1/trace/order")
  DefaultServerResponse<String, StandardError> createTraceOrder(
      @RequestBody TraceOrderRequest traceOrderRequest);
}
