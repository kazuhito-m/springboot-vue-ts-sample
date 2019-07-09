package com.github.kazuhito_m.mysample.infrastructure.datasource.profile;

import com.github.kazuhito_m.mysample.domain.model.profile.ProfileImage;
import com.github.kazuhito_m.mysample.domain.model.profile.ProfileRepository;
import com.github.kazuhito_m.mysample.domain.model.user.UserIdentifier;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileDatasource implements ProfileRepository {
    final ProfileImageDao profileImageDao;

    @Override
    public void registerImage(ProfileImage image) {
        ProfileImageTable record = new ProfileImageTable(image);
        profileImageDao.delete(record);
        profileImageDao.register(record);
    }

    @Override
    public ProfileImage findBy(UserIdentifier id) {
        return profileImageDao.findBy(id.value())
                .map(ProfileImageTable::toProfileImage)
                .get();
    }

    @Override
    public boolean isExist(UserIdentifier identifier) {
        return profileImageDao.findBy(identifier.value()).isPresent();
    }

    public ProfileDatasource(ProfileImageDao profileImageDao) {
        this.profileImageDao = profileImageDao;
    }
}
