package com.example.fc_plaza_service.infrastructure.entrypoint.controller;

import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.BAD_REQUEST;
import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.CONFLICT;
import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.CREATED;
import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.OK;
import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.OK_INT;
import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.SERVER_ERROR;
import static com.example.fc_plaza_service.domain.constants.MsgConst.BAD_REQUEST_MSG;
import static com.example.fc_plaza_service.domain.constants.MsgConst.DISH_ALREADY_EXISTS_MSG;
import static com.example.fc_plaza_service.domain.constants.MsgConst.DISH_CREATED_SUCCESSFULLY_MSG;
import static com.example.fc_plaza_service.domain.constants.MsgConst.DISH_UPDATED_SUCCESSFULLY_MSG;
import static com.example.fc_plaza_service.domain.constants.MsgConst.SERVER_ERROR_MSG;
import static com.example.fc_plaza_service.domain.constants.RouterConst.DISH_BASE_PATH;
import static com.example.fc_plaza_service.domain.constants.RouterConst.ENABLE_BASE_PATH;
import static com.example.fc_plaza_service.domain.constants.RouterConst.MENU_BASE_PATH;
import static com.example.fc_plaza_service.domain.constants.RouterConst.RESTAURANT_BASE_PATH;
import static com.example.fc_plaza_service.domain.constants.SwaggerConst.CREATE_DISH_OPERATION;
import static com.example.fc_plaza_service.domain.constants.SwaggerConst.ENABLE_DISH_OPERATION;
import static com.example.fc_plaza_service.domain.constants.SwaggerConst.GET_MENU_OPERATION;
import static com.example.fc_plaza_service.domain.constants.SwaggerConst.UPDATE_DISH_OPERATION;
import static com.example.fc_plaza_service.domain.enums.ServerResponses.DISH_CREATED_SUCCESSFULLY;
import static com.example.fc_plaza_service.domain.enums.ServerResponses.DISH_UPDATED_SUCCESSFULLY;

import com.example.fc_plaza_service.application.service.DishApplicationService;
import com.example.fc_plaza_service.domain.enums.ProductStatus;
import com.example.fc_plaza_service.domain.exceptions.StandardError;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.DefaultServerResponse;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.DishRequest;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.request.DishUpdateRequest;
import com.example.fc_plaza_service.infrastructure.entrypoint.dto.response.DishResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(RESTAURANT_BASE_PATH)
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
  @PostMapping("/{restaurant_id}" + DISH_BASE_PATH)
  @PreAuthorize("hasAuthority('landlord')")
  public ResponseEntity<DefaultServerResponse<String, StandardError>> createDish(
      @PathVariable(name = "restaurant_id") Long restaurantId,
      @Valid @RequestBody final DishRequest dishRequest) {
    dishApplicationService.createDish(restaurantId, dishRequest);
    return ResponseEntity.status(DISH_CREATED_SUCCESSFULLY.getHttpStatus())
        .body(new DefaultServerResponse<>(DISH_CREATED_SUCCESSFULLY.getMessage(), null));
  }

  @Operation(summary = UPDATE_DISH_OPERATION)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = OK, description = DISH_UPDATED_SUCCESSFULLY_MSG),
        @ApiResponse(responseCode = BAD_REQUEST, description = BAD_REQUEST_MSG),
        @ApiResponse(responseCode = SERVER_ERROR, description = SERVER_ERROR_MSG),
      })
  @PatchMapping("/{restaurant_id}" + DISH_BASE_PATH + "/{dish_id}")
  @PreAuthorize("hasAuthority('landlord')")
  public ResponseEntity<DefaultServerResponse<String, StandardError>> updateDish(
      @PathVariable(name = "restaurant_id") Long restaurantId,
      @PathVariable(name = "dish_id") Long dishId,
      @Valid @RequestBody final DishUpdateRequest dishUpdateRequest) {
    dishApplicationService.updateDish(restaurantId, dishId, dishUpdateRequest);
    return ResponseEntity.status(DISH_UPDATED_SUCCESSFULLY.getHttpStatus())
        .body(new DefaultServerResponse<>(DISH_UPDATED_SUCCESSFULLY.getMessage(), null));
  }

  @Operation(summary = ENABLE_DISH_OPERATION)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = OK, description = DISH_UPDATED_SUCCESSFULLY_MSG),
        @ApiResponse(responseCode = BAD_REQUEST, description = BAD_REQUEST_MSG),
        @ApiResponse(responseCode = SERVER_ERROR, description = SERVER_ERROR_MSG),
      })
  @PatchMapping("/{restaurant_id}" + DISH_BASE_PATH + "/{dish_id}" + ENABLE_BASE_PATH)
  @PreAuthorize("hasAuthority('landlord')")
  public ResponseEntity<DefaultServerResponse<String, StandardError>> enableDish(
      @PathVariable(name = "restaurant_id") Long restaurantId,
      @PathVariable(name = "dish_id") Long dishId,
      @PathParam("status") ProductStatus status) {
    dishApplicationService.updateStatusDish(restaurantId, dishId, status);
    return ResponseEntity.status(DISH_UPDATED_SUCCESSFULLY.getHttpStatus())
        .body(new DefaultServerResponse<>(DISH_UPDATED_SUCCESSFULLY.getMessage(), null));
  }

  @Operation(summary = GET_MENU_OPERATION)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = OK, description = ""),
        @ApiResponse(responseCode = SERVER_ERROR, description = SERVER_ERROR_MSG),
      })
  @GetMapping("/{restaurant_id}" + MENU_BASE_PATH)
  @PreAuthorize("hasAuthority('client')")
  public ResponseEntity<DefaultServerResponse<List<DishResponse>, StandardError>> getRestaurantMenu(
      @PathVariable(name = "restaurant_id") Long restaurantId,
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "size", defaultValue = "10") Integer size) {
    return ResponseEntity.status(OK_INT)
        .body(
            new DefaultServerResponse<>(
                dishApplicationService.getRestaurantMenu(restaurantId, page, size), null));
  }
}
