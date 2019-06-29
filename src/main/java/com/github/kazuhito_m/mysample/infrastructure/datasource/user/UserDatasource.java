package com.github.kazuhito_m.mysample.infrastructure.datasource.user;

import com.github.kazuhito_m.mysample.domain.model.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Repository
public class UserDatasource implements UserRepository {
    private static Logger LOGGER = LoggerFactory.getLogger(UserDatasource.class);

    final UserDao dao;

    @Override
    public User findBy(UserIdentifier id) {
        return dao.findBy(id.value())
                .map(UserTable::toUser)
                .get();
    }

    @Override
    public boolean isExist(User user) {
        return dao.findBy(user.identifier().value()).isPresent();
    }

    @Override
    public UserSummaries list() {
        List<UserSummary> summaries = dao.list()
                .stream()
                .map(userTable -> userTable.toUserSummary())
                .collect(toList());
        return new UserSummaries(summaries);
    }

    @Override
    public User prototype() {
        return new User();
    }

    @Override
    public void register(User user) {
        LOGGER.info("登録前値L:" + user);
        dao.register(new UserTable(user));
    }

    @Override
    public void update(User user) {
        UserTable record = new UserTable(user);
        dao.delete(record);
        dao.register(record);
    }

    @Override
    public void delete(User user) {
        dao.delete(new UserTable(user));
    }

    public UserDatasource(UserDao dao) {
        this.dao = dao;
    }
}
