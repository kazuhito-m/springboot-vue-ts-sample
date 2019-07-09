package com.github.kazuhito_m.mysample.presentation.api.profile;

import com.github.kazuhito_m.mysample.application.service.user.UserService;
import com.github.kazuhito_m.mysample.domain.model.user.UserIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/user/profile/image")
public class UserProfileRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProfileRestController.class);

    final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void register(@RequestParam("userIdentifier") @Valid final UserIdentifier userIdentifier,
                  @RequestParam("file") MultipartFile file) {
        LOGGER.info("userIdentifier:" + userIdentifier);
        LOGGER.info("file:" + file.getOriginalFilename());
    }

    UserProfileRestController(UserService userService) {
        this.userService = userService;
    }
}
