package com.github.kazuhito_m.mysample.presentation.api.profile;

import com.github.kazuhito_m.mysample.application.service.profile.ProfileService;
import com.github.kazuhito_m.mysample.application.service.user.UserService;
import com.github.kazuhito_m.mysample.domain.basic.DataNotExistsException;
import com.github.kazuhito_m.mysample.domain.model.profile.ProfileImage;
import com.github.kazuhito_m.mysample.domain.model.user.UserIdentifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@Validated
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

    @GetMapping
    void downloadImage(@RequestParam("userIdentifier") @Valid UserIdentifier userIdentifier,
                       HttpServletResponse response) throws IOException {
        if (!profileService.isExist(userIdentifier)) throw new DataNotExistsException();
        ProfileImage image = profileService.imageOf(userIdentifier);
        write(image, response);
    }

    private void write(ProfileImage image, HttpServletResponse response) throws IOException {
        response.addHeader("Content-Type", "application/octet-stream");
        response.addHeader("Content-Disposition", "attachment; filename=profileImage.png");
        ByteArrayInputStream bytes = new ByteArrayInputStream(image.binary().value());
        bytes.transferTo(response.getOutputStream());
    }

    UserProfileRestController(UserService userService, ProfileService profileService) {
        this.userService = userService;
        this.profileService = profileService;
    }
}
