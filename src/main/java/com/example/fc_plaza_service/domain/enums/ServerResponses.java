package com.example.fc_plaza_service.domain.enums;

import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.BAD_REQUEST_INT;
import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.CONFLICT_INT;
import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.CREATED_INT;
import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.OK_INT;
import static com.example.fc_plaza_service.domain.constants.HttpStatusConst.SERVER_ERROR_INT;

import com.example.fc_plaza_service.domain.constants.MsgConst;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServerResponses {
  BAD_REQUEST("E000", BAD_REQUEST_INT, MsgConst.BAD_REQUEST_MSG),
  SERVER_ERROR("E001", SERVER_ERROR_INT, MsgConst.SERVER_ERROR_MSG),
  RESTAURANT_CREATED_SUCCESSFULLY("", CREATED_INT, MsgConst.RESTAURANT_CREATED_SUCCESSFULLY_MSG),
  RESTAURANT_ALREADY_EXISTS("E002", CONFLICT_INT, MsgConst.RESTAURANT_ALREADY_EXISTS_MSG),
  DISH_CREATED_SUCCESSFULLY("", CREATED_INT, MsgConst.DISH_CREATED_SUCCESSFULLY_MSG),
  DISH_ALREADY_EXISTS("E003", CONFLICT_INT, MsgConst.DISH_ALREADY_EXISTS_MSG),
  DISH_UPDATED_SUCCESSFULLY("", OK_INT, MsgConst.DISH_UPDATED_SUCCESSFULLY_MSG),
  ORDER_CREATED_SUCCESSFULLY("", CREATED_INT, MsgConst.ORDER_CREATED_SUCCESSFULLY_MSG),
  ORDER_CONFLICT("E004", CONFLICT_INT, MsgConst.ORDER_CONFLICT_MSG);

  private final String code;
  private final int httpStatus;
  private final String message;
}
