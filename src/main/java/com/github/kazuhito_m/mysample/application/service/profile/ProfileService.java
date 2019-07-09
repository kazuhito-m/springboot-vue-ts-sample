package com.github.kazuhito_m.mysample.application.service.profile;

import com.github.kazuhito_m.mysample.domain.model.profile.ProfileImage;
import com.github.kazuhito_m.mysample.domain.model.profile.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    final ProfileRepository repository;

    public void registerImage(ProfileImage profileImage) {
        repository.registerImage(profileImage);
    }

    public ProfileService(ProfileRepository repository) {
        this.repository = repository;
    }
}
