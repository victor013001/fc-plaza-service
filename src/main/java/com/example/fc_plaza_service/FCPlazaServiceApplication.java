package com.example.fc_plaza_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class FCPlazaServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(FCPlazaServiceApplication.class, args);
  }
}
