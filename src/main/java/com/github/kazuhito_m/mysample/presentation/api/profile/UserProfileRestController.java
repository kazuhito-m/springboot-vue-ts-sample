package com.github.kazuhito_m.mysample.presentation.api.profile;

import com.github.kazuhito_m.mysample.application.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/profile/image")
public class UserProfileRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileRestController.class);

    final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void register(@ModelAttribute @Valid UserProfileImageResource resource) {
        LOGGER.info("userIdentifier:" + resource.userIdentifier);
        LOGGER.info("file:" + resource.file.getOriginalFilename());
    }

    UserProfileRestController(UserService userService) {
        this.userService = userService;
    }
}
