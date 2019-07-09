package com.github.kazuhito_m.mysample.infrastructure.datasource.profile;

import com.github.kazuhito_m.mysample.domain.model.profile.ProfileImage;
import com.github.kazuhito_m.mysample.domain.model.profile.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class ProfileDatasource implements ProfileRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileDatasource.class);

    @Override
    public void registerImage(ProfileImage image) {
        // TODO 実装。
        LOGGER.info("送信されてきたデータサイズ:" + image.binary().value().length);
    }
}
