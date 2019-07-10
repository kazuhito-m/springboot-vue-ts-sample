package com.github.kazuhito_m.mysample.infrastructure.datasource.profile;

import com.github.kazuhito_m.mysample.domain.model.profile.ImageBinary;
import com.github.kazuhito_m.mysample.domain.model.profile.ProfileImage;
import com.github.kazuhito_m.mysample.domain.model.user.UserIdentifier;
import org.seasar.doma.Entity;
import org.seasar.doma.Id;
import org.seasar.doma.Table;

@Entity(immutable = true)
@Table(name = "profiles.profile_images")
public class ProfileImageTable {
    @Id
    private final String userId;
    private final byte[] imageBinary;

    public ProfileImage toProfileImage() {
        return new ProfileImage(
                new UserIdentifier(userId),
                new ImageBinary(imageBinary)
        );
    }

    public ProfileImageTable(ProfileImage profileImage) {
        this(profileImage.userIdentifier().toString(), profileImage.binary().value());
    }

    public ProfileImageTable(String userId, byte[] imageBinary) {
        this.userId = userId;
        this.imageBinary = imageBinary;
    }
}
