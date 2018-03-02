package com.aa.opengames.authentication;

public class LoginResponse {
  private LoginResponseStatus loginResponseStatus;
  private String message;

  public LoginResponse() {
  }

  public LoginResponse(LoginResponseStatus loginResponseStatus, String message) {
    this.loginResponseStatus = loginResponseStatus;
    this.message = message;
  }

  public LoginResponseStatus getLoginResponseStatus() {
    return loginResponseStatus;
  }

  public void setLoginResponseStatus(LoginResponseStatus loginResponseStatus) {
    this.loginResponseStatus = loginResponseStatus;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public enum LoginResponseStatus {
    SUCCESS, ERROR
  }
}
