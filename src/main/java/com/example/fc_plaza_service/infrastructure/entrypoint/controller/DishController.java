package com.example.fc_plaza_service.infrastructure.entrypoint.controller;

import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.BAD_REQUEST;
import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.CONFLICT;
import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.CREATED;
import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.SERVER_ERROR;
import static com.example.fc_plaza_service.domain.constants.MsgConst.BAD_REQUEST_MSG;
import static com.example.fc_plaza_service.domain.constants.MsgConst.DISH_ALREADY_EXISTS_MSG;
import static com.example.fc_plaza_service.domain.constants.MsgConst.DISH_CREATED_SUCCESSFULLY_MSG;
import static com.example.fc_plaza_service.domain.constants.MsgConst.SERVER_ERROR_MSG;
import static com.example.fc_plaza_service.domain.constants.RouterConst.DISH_BASE_PATH;
import static com.example.fc_plaza_service.domain.constants.RouterConst.RESTAURANT_BASE_PATH;
import static com.example.fc_plaza_service.domain.constants.SwaggerConst.CREATE_DISH_OPERATION;
import static com.example.fc_plaza_service.domain.enums.ServerResponses.DISH_CREATED_SUCCESSFULLY;

import com.example.fc_plaza_service.application.service.DishApplicationService;
import com.example.fc_plaza_service.domain.exceptions.StandardError;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.DefaultServerResponse;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.DishRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(RESTAURANT_BASE_PATH + DISH_BASE_PATH)
public class DishController {

  private final DishApplicationService dishApplicationService;

  @Operation(summary = CREATE_DISH_OPERATION)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = CREATED, description = DISH_CREATED_SUCCESSFULLY_MSG),
        @ApiResponse(responseCode = CONFLICT, description = DISH_ALREADY_EXISTS_MSG),
        @ApiResponse(responseCode = BAD_REQUEST, description = BAD_REQUEST_MSG),
        @ApiResponse(responseCode = SERVER_ERROR, description = SERVER_ERROR_MSG),
      })
  @PostMapping("/{restaurant_id}")
  public ResponseEntity<DefaultServerResponse<String, StandardError>> createDish(
      @PathVariable(name = "restaurant_id") Long restaurantId,
      @Valid @RequestBody final DishRequest dishRequest) {
    dishApplicationService.createDish(restaurantId, dishRequest);
    return ResponseEntity.status(DISH_CREATED_SUCCESSFULLY.getHttpStatus())
        .body(new DefaultServerResponse<>(DISH_CREATED_SUCCESSFULLY.getMessage(), null));
  }
}
