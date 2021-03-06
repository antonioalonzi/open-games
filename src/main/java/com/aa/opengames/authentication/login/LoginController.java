package com.aa.opengames.authentication.login;

import com.aa.opengames.authentication.context.SecurityContextHolder;
import com.aa.opengames.event.Event;
import com.aa.opengames.event.EventSender;
import com.aa.opengames.user.User;
import com.aa.opengames.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

import static com.aa.opengames.event.EventResponse.ResponseStatus.ERROR;
import static com.aa.opengames.event.EventResponse.ResponseStatus.SUCCESS;

@Controller
public class LoginController {

    private final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    private UserRepository userRepository;
    private EventSender eventSender;

    @Autowired
    public LoginController(UserRepository userRepository, EventSender eventSender) {
        this.userRepository = userRepository;
        this.eventSender = eventSender;
    }

    @MessageMapping("/auth/login")
    public void login(SimpMessageHeaderAccessor headerAccessor, LoginRequest loginRequest) {
        String sessionId = headerAccessor.getSessionId();
        LOGGER.info("Login request received for username '{}'", loginRequest.getUsername());
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());

        if (!user.isPresent()) {
            User currentUser = User.builder().username(loginRequest.getUsername()).token(sessionId).build();
            userRepository.addUser(currentUser);
            SecurityContextHolder.addUser(sessionId, currentUser);
            eventSender.sendToUser(sessionId, Event.builder()
                    .type(LoginResponse.EVENT_TYPE)
                    .value(LoginResponse.builder()
                            .responseStatus(SUCCESS)
                            .message("Login Successful.")
                            .userDetails(LoginResponse.UserDetails.builder()
                                    .token(sessionId)
                                    .username(currentUser.getUsername())
                                    .build())
                            .build())
                    .build());

            eventSender.sendToAll(Event.builder()
                    .type(UserLoggedInEvent.EVENT_TYPE)
                    .value(UserLoggedInEvent.builder().username(currentUser.getUsername()).build())
                    .build()
            );

        } else {
            eventSender.sendToUser(sessionId, Event.builder()
                    .type(LoginResponse.EVENT_TYPE)
                    .value(LoginResponse.builder()
                            .responseStatus(ERROR)
                            .message("Username '" + loginRequest.getUsername() + "' is already used. Please choose another one.")
                            .build())
                    .build());
        }
    }

}
