package com.example.fc_plaza_service.domain.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class HttpStatusConst {
  public static final String BAD_REQUEST = "400";
  public static final String SERVER_ERROR = "500";
  public static final String CREATED = "201";
  public static final String CONFLICT = "409";

  public static final int BAD_REQUEST_INT = 400;
  public static final int SERVER_ERROR_INT = 500;
  public static final int CREATED_INT = 201;
  public static final int CONFLICT_INT = 409;
}
