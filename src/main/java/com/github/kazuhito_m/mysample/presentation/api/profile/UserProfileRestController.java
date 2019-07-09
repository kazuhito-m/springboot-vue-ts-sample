package com.github.kazuhito_m.mysample.presentation.api.profile;

import com.github.kazuhito_m.mysample.application.service.profile.ProfileService;
import com.github.kazuhito_m.mysample.application.service.user.UserService;
import com.github.kazuhito_m.mysample.domain.basic.DataNotExistsException;
import com.github.kazuhito_m.mysample.domain.model.profile.ProfileImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user/profile/image")
public class UserProfileRestController {
    final UserService userService;
    final ProfileService profileService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void register(@ModelAttribute @Valid UserProfileImageResource resource) {
        ProfileImage profileImage = resource.toProfileImage();
        if (!userService.isExist(profileImage.userIdentifier())) throw new DataNotExistsException();
        profileService.registerImage(profileImage);
    }

    UserProfileRestController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }
}
