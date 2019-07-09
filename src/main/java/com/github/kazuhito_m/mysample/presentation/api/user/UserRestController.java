package com.github.kazuhito_m.mysample.presentation.api.user;

import com.github.kazuhito_m.mysample.application.service.user.UserService;
import com.github.kazuhito_m.mysample.domain.model.user.GenderType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    final UserService userService;

    @GetMapping
    List<UserResponse> users() {
        return userService.allUsers()
                .list()
                .stream()
                .map(UserResponse::new)
                .collect(toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void register(@Valid @RequestBody UserRegistrationResource resource) {
        userService.register(resource.toUser());
    }

    @GetMapping("/genderTypes")
    List<String> genderTypes() {
        return Stream.of(GenderType.values())
                .map(GenderType::name)
                .collect(toList());
    }

    UserRestController(UserService userService) {
        this.userService = userService;
    }
}
