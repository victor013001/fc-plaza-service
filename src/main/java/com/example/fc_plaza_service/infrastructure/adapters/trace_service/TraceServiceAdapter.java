package com.example.fc_plaza_service.infrastructure.adapters.trace_service;

import com.example.fc_plaza_service.domain.model.TraceOrderRequest;
import com.example.fc_plaza_service.domain.spi.TraceServicePort;
import com.example.fc_plaza_service.infrastructure.adapters.trace_service.feign.TraceFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TraceServiceAdapter implements TraceServicePort {
  private static final String LOG_PREFIX = "[TRACE_SERVICE_ADAPTER] >>> ";

  private final TraceFeignClient traceFeignClient;

  @Override
  public void trace(TraceOrderRequest traceOrderRequest) {
    log.info("{} Creating trace for order: {}", LOG_PREFIX, traceOrderRequest.orderId());
    traceFeignClient.createTraceOrder(traceOrderRequest);
  }
}
