package com.github.kazuhito_m.mysample.infrastructure.datasource.user;

import com.github.kazuhito_m.mysample.domain.model.user.User;
import com.github.kazuhito_m.mysample.domain.model.user.UserIdentifier;
import com.github.kazuhito_m.mysample.domain.model.user.UserRepository;
import com.github.kazuhito_m.mysample.domain.model.user.UserSummaries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDatasource implements UserRepository {
    @Autowired
    UserMapper mapper;

    @Override
    public User findBy(UserIdentifier id) {
        return mapper.findBy(id);
    }

    @Override
    public Boolean isExist(User user) {
        if (findBy(user.identifier()) == null) return false;
        return true;
    }

    @Override
    public UserSummaries list() {
        return new UserSummaries(mapper.list());
    }

    @Override
    public User prototype() {
        return new User();
    }

    @Override
    public void register(User user) {
        mapper.register(user);
    }

    @Override
    public void update(User user) {
        mapper.delete(user);
        mapper.register(user);
    }

    @Override
    public void delete(User user) {
        mapper.delete(user);
    }
}
