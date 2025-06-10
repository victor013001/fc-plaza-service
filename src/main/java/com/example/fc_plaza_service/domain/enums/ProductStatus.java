package com.example.fc_plaza_service.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {
  ENABLED(true),
  DISABLED(false);

  private final boolean active;
}
