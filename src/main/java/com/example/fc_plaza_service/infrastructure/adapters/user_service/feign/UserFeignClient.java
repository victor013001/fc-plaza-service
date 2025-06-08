package com.example.fc_plaza_service.infrastructure.adapters.user_service.feign;

import com.example.fc_plaza_service.domain.exceptions.StandardError;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.DefaultServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "http://localhost:8080")
public interface UserFeignClient {
  @GetMapping("/user/landlord/exists")
  DefaultServerResponse<Boolean, StandardError> findLandlordById(
      @RequestParam("userId") Long landlordId);
}
