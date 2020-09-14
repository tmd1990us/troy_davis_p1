package com.revature.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.services.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class ValidationHelper {

    private final UserService userService = new UserService();

    public boolean process(HttpServletRequest req) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        switch (req.getRequestURI()) {
            case "/ers/email.validate":
            case "/email.validate":
                String email = mapper.readValue(req.getInputStream(), String.class);
                return userService.isEmailAvailable(email);
            case "/ers/username.validate":
            case "/username.validate":
                String username = mapper.readValue(req.getInputStream(), String.class);
                return userService.isUsernameAvailable(username);
            default:
                return false;
        }

    }
}
