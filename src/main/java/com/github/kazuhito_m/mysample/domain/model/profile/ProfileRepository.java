package com.github.kazuhito_m.mysample.domain.model.profile;

import com.github.kazuhito_m.mysample.domain.model.user.UserIdentifier;

public interface ProfileRepository {
    void registerImage(ProfileImage image);

    ProfileImage findBy(UserIdentifier id);

    boolean isExist(UserIdentifier userIdentifier);
}
