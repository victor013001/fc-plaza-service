package com.example.fc_plaza_service.domain.spi;

import com.example.fc_plaza_service.domain.model.TraceOrderRequest;

public interface TraceServicePort {
  void trace(TraceOrderRequest traceOrderRequest);
}
