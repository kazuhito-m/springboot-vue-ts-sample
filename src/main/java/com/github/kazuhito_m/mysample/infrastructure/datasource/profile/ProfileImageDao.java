package com.github.kazuhito_m.mysample.infrastructure.datasource.profile;

import com.github.kazuhito_m.mysample.infrastructure.datasource.ConfigAutowireable;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.Result;

import java.util.Optional;

@Dao
@ConfigAutowireable
public interface ProfileImageDao {
    @Select
    Optional<ProfileImageTable> findBy(String userId);

    @Insert
    Result<ProfileImageTable> register(ProfileImageTable profileImage);

    @Delete
    Result<ProfileImageTable> delete(ProfileImageTable profileImage);
}
