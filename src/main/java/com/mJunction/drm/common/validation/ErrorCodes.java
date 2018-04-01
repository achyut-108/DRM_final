package com.mJunction.drm.common.validation;


/**
 * @author siddhartha.kumar on 04-Mar-2016
 */

public enum ErrorCodes {

  INVALID_USER_ID("MJ_ERROR_600", "Your User Id is not Valid"),
  INVALID_PASSWORD("MJ_ERROR_601", "Your Password is not Valid"),
  INVALID_DATA("MJ_ERROR_602", "Invalid data type"),
  AUTHENTICATION_FAILED("MJ_ERROR_603","Not Authenticated");

  private final String code;
  private final String description;

  private ErrorCodes(String code, String description) {
    this.code = code;
    this.description = description;
  }

  public String getDescription() {
     return description;
  }

  public String getCode() {
     return code;
  }

  @Override
  public String toString() {
    return code + ": " + description;
  }

}