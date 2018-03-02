package com.aa.opengames.authentication;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.aa.opengames.authentication.LoginResponse.LoginResponseStatus.SUCCESS;

@RestController
public class LoginController {

  @RequestMapping("/api/login")
  public LoginResponse login(@RequestBody LoginRequest loginRequest) {
    return new LoginResponse(SUCCESS, "Login Successful");
  }
}
