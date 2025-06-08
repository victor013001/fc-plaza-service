package com.example.non_reactive_archetype.infrastructure.exceptions;

import static com.example.non_reactive_archetype.domain.enums.ServerResponses.SERVER_ERROR;

import com.example.non_reactive_archetype.domain.exceptions.StandardError;
import com.example.non_reactive_archetype.domain.exceptions.StandardException;
import com.example.non_reactive_archetype.infrastructure.entrypoint.dto.DefaultServerResponse;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
  private static final String LOG_PREFIX = "[CONTROLLER_ADVISOR] >>> ";

  @ExceptionHandler(StandardException.class)
  protected ResponseEntity<DefaultServerResponse<Object, StandardError>> handlerStandardException(
      StandardException standardException) {
    log.error(
        "{} Standard exception with code caught: {}",
        LOG_PREFIX,
        standardException.getStandardError().getCode());
    return ResponseEntity.status(standardException.getHttpStatus())
        .body(new DefaultServerResponse<>(null, standardException.getStandardError()));
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<DefaultServerResponse<Object, StandardError>> handleGenericException(
      Exception ex) {
    return ResponseEntity.internalServerError()
        .body(new DefaultServerResponse<>(null, buildStandardError(ex.getMessage())));
  }

  private StandardError buildStandardError(String message) {
    log.error("{} Exception caught: {}", LOG_PREFIX, message);
    return StandardError.builder()
        .code(SERVER_ERROR.getCode())
        .description(SERVER_ERROR.getMessage())
        .timestamp(LocalDateTime.now())
        .build();
  }
}
