package com.aa.opengames.authentication.login;

public class LoginResponse {
  private LoginResponseStatus loginResponseStatus;
  private String message;
  private String token;

  public LoginResponse() {
  }

  public LoginResponse(LoginResponseStatus loginResponseStatus, String message) {
    this(loginResponseStatus, message, null);
  }

  public LoginResponse(LoginResponseStatus loginResponseStatus, String message, String token) {
    this.loginResponseStatus = loginResponseStatus;
    this.message = message;
    this.token = token;
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

  public String getToken() {
    return token;
  }

  public enum LoginResponseStatus {
    SUCCESS, ERROR
  }
}
