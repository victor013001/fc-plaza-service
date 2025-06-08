package com.example.fc_plaza_service.domain.exceptions.standard_exception;

import com.example.fc_plaza_service.domain.enums.ServerResponses;
import com.example.fc_plaza_service.domain.exceptions.StandardException;

public class ServerError extends StandardException {
  public ServerError() {
    super(ServerResponses.SERVER_ERROR);
  }
}
