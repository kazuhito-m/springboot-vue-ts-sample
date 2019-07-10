package com.github.kazuhito_m.mysample.domain.model.user;

public interface UserRepository {
    User findBy(UserIdentifier id);

    boolean isExist(UserIdentifier identifier);

    UserSummaries list();

    User prototype();

    void register(User user);

    void update(User user);

    void delete(User user);
}
