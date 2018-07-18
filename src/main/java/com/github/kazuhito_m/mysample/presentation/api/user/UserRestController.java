package com.github.kazuhito_m.mysample.presentation.api.user;

import com.github.kazuhito_m.mysample.application.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    final UserService userService;

    @GetMapping("")
    List<UserResponse> users() {
        return userService.allUsers()
            .list()
            .stream()
            .map(UserResponse::new)
            .collect(toList());
    }

    UserRestController(UserService userService) {
        this.userService = userService;
    }
}
