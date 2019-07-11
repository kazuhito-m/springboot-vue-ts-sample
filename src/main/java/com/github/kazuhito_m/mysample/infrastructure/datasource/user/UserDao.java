package com.github.kazuhito_m.mysample.infrastructure.datasource.user;

import com.github.kazuhito_m.mysample.infrastructure.datasource.ConfigAutowireable;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.jdbc.Result;

import java.util.List;
import java.util.Optional;

@Dao
@ConfigAutowireable
public interface UserDao {
    @Select
    Optional<UserTable> findBy(String userId);

    @Select
    List<UserTable> list();

    @Insert
    Result<UserTable> register(UserTable user);

    @Delete
    Result<UserTable> delete(UserTable user);
}
