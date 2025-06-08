package com.example.fc_plaza_service.domain.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MsgConst {
  public static final String BAD_REQUEST_MSG =
      "The request could not be processed due to invalid or incomplete data.";
  public static final String SERVER_ERROR_MSG =
      "An unexpected server error occurred. Please try again later.";
  public static final String RESTAURANT_CREATED_SUCCESSFULLY_MSG =
      "The restaurant was created successfully.";
  public static final String RESTAURANT_ALREADY_EXISTS_MSG =
      "The restaurant could not be created due to a data conflict.";
  public static final String DISH_CREATED_SUCCESSFULLY_MSG = "The dish was created successfully.";
  public static final String DISH_ALREADY_EXISTS_MSG =
      "The dish could not be created due to a data conflict.";
}
