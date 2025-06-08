package com.example.non_reactive_archetype.domain.enums;

import static com.example.non_reactive_archetype.domain.constants.HttpStatusConst.BAD_REQUEST_INT;
import static com.example.non_reactive_archetype.domain.constants.HttpStatusConst.SERVER_ERROR_INT;

import com.example.non_reactive_archetype.domain.constants.MsgConst;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServerResponses {
  BAD_REQUEST("E000", BAD_REQUEST_INT, MsgConst.BAD_REQUEST_MSG),
  SERVER_ERROR("E001", SERVER_ERROR_INT, MsgConst.SERVER_ERROR_MSG);

  private final String code;
  private final int httpStatus;
  private final String message;
}
