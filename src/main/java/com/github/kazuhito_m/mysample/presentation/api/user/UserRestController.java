package com.github.kazuhito_m.mysample.presentation.api.user;

import com.github.kazuhito_m.mysample.application.service.UserService;
import com.github.kazuhito_m.mysample.domain.model.user.UserSummaries;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    final UserService userService;

    @GetMapping("")
    UserSummaries users() {
        return userService.list();
    }

    UserRestController(UserService userService) {
        this.userService = userService;
    }
}
