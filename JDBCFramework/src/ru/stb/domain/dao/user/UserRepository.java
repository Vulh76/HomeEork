package ru.stb.domain.dao.user;

import ru.stb.domain.dao.AbstractRepository;
import ru.stb.domain.dao.Mapper;
import ru.stb.domain.entity.user.User;

public class UserRepository extends AbstractRepository<User> {

    public UserRepository(String jdbcUrl, String user, String password, String driver, Mapper<User> mapper) {
        super(jdbcUrl, user, password, driver, mapper);
    }
}
