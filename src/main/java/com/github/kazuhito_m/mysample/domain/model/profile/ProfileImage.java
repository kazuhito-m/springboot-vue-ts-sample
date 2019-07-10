package com.github.kazuhito_m.mysample.domain.model.profile;

import com.github.kazuhito_m.mysample.domain.model.user.UserIdentifier;

public class ProfileImage {
    final UserIdentifier userIdentifier;
    final ImageBinary binary;

    public ProfileImage(UserIdentifier userIdentifier, ImageBinary binary) {
        this.userIdentifier = userIdentifier;
        this.binary = binary;
    }

    public UserIdentifier userIdentifier() {
        return userIdentifier;
    }

    public ImageBinary binary() {
        return binary;
    }
}
