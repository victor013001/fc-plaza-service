package com.example.non_reactive_archetype.domain.exceptions.standard_exception;

import com.example.non_reactive_archetype.domain.enums.ServerResponses;
import com.example.non_reactive_archetype.domain.exceptions.StandardException;

public class BadRequest extends StandardException {
  public BadRequest() {
    super(ServerResponses.BAD_REQUEST);
  }
}
