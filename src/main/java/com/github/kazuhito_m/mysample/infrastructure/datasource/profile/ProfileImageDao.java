package com.github.kazuhito_m.mysample.infrastructure.datasource.profile;

import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

import java.util.Optional;

@ConfigAutowireable
@Dao
public interface ProfileImageDao {
    @Select
    Optional<ProfileImageTable> findBy(String userId);

    @Insert
    Result<ProfileImageTable> register(ProfileImageTable profileImage);

    @Delete(sqlFile = true)
    int delete(String userId);
}
