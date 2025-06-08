package com.example.fc_plaza_service;

import com.example.fc_plaza_service.config.TestcontainersConfig;
import org.springframework.boot.SpringApplication;

public class TestFCPlazaServiceApplication {
  public static void main(String[] args) {
    SpringApplication.from(FCPlazaServiceApplication::main)
        .with(TestcontainersConfig.class)
        .run(args);
  }
}
