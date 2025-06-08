package com.example.non_reactive_archetype.domain.exceptions.standard_exception;

import com.example.non_reactive_archetype.domain.enums.ServerResponses;
import com.example.non_reactive_archetype.domain.exceptions.StandardException;

public class ServerError extends StandardException {
  public ServerError() {
    super(ServerResponses.SERVER_ERROR);
  }
}
