package com.example.fc_plaza_service.infrastructure.adapters.user_service.feign;

import com.example.fc_plaza_service.domain.exceptions.StandardError;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.DefaultServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8080")
public interface UserFeignClient {
  @GetMapping("/api/v1/user/landlord/exists/{landlord_id}")
  DefaultServerResponse<Boolean, StandardError> findLandlordById(
      @PathVariable(name = "landlord_id") Long landlordId);

  @GetMapping("/api/v1/user/landlord/{landlord_id}/belongs")
  DefaultServerResponse<Boolean, StandardError> doesLandlordBelongToEmail(
      @PathVariable("landlord_id") Long landlordId);

  @GetMapping("/api/v1/user")
  DefaultServerResponse<Long, StandardError> getCurrentUserId();

  @GetMapping("/api/v1/user/employee/restaurant")
  DefaultServerResponse<Long, StandardError> getEmployeeRestaurant();
}
