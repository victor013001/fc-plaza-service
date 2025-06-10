package com.example.fc_plaza_service.application.config;

import static com.example.fc_plaza_service.domain.constants.HeaderConst.AUTHORIZATION;
import static com.example.fc_plaza_service.domain.constants.HeaderConst.BEARER;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class FeignClientConfig {
  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if (authentication != null && authentication.getCredentials() instanceof String token) {
        requestTemplate.header(AUTHORIZATION, BEARER + token);
      }
    };
  }
}
