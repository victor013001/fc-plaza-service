package com.example.fc_plaza_service.infrastructure.entrypoint.controller;

import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.BAD_REQUEST;
import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.CONFLICT;
import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.CREATED;
import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.SERVER_ERROR;
import static com.example.fc_plaza_service.domain.constants.MsgConst.BAD_REQUEST_MSG;
import static com.example.fc_plaza_service.domain.constants.MsgConst.RESTAURANT_ALREADY_EXISTS_MSG;
import static com.example.fc_plaza_service.domain.constants.MsgConst.RESTAURANT_CREATED_SUCCESSFULLY_MSG;
import static com.example.fc_plaza_service.domain.constants.MsgConst.SERVER_ERROR_MSG;
import static com.example.fc_plaza_service.domain.constants.RouterConst.RESTAURANT_BASE_PATH;
import static com.example.fc_plaza_service.domain.constants.SwaggerConst.CREATE_RESTAURANT_OPERATION;
import static com.example.fc_plaza_service.domain.enums.ServerResponses.RESTAURANT_CREATED_SUCCESSFULLY;

import com.example.fc_plaza_service.application.service.RestaurantApplicationService;
import com.example.fc_plaza_service.domain.exceptions.StandardError;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.DefaultServerResponse;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.RestaurantRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(RESTAURANT_BASE_PATH)
public class RestaurantController {

  private final RestaurantApplicationService restaurantApplicationService;

  @Operation(summary = CREATE_RESTAURANT_OPERATION)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = CREATED, description = RESTAURANT_CREATED_SUCCESSFULLY_MSG),
        @ApiResponse(responseCode = CONFLICT, description = RESTAURANT_ALREADY_EXISTS_MSG),
        @ApiResponse(responseCode = BAD_REQUEST, description = BAD_REQUEST_MSG),
        @ApiResponse(responseCode = SERVER_ERROR, description = SERVER_ERROR_MSG),
      })
  @PostMapping()
  public ResponseEntity<DefaultServerResponse<String, StandardError>> createLandlord(
      @Valid @RequestBody final RestaurantRequest restaurantRequest) {
    restaurantApplicationService.createRestaurant(restaurantRequest);
    return ResponseEntity.status(RESTAURANT_CREATED_SUCCESSFULLY.getHttpStatus())
        .body(new DefaultServerResponse<>(RESTAURANT_CREATED_SUCCESSFULLY.getMessage(), null));
  }
}
