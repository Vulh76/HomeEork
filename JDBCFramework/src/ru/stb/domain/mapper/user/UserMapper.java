package ru.stb.domain.mapper.user;

import ru.stb.domain.dao.Mapper;
import ru.stb.domain.entity.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserMapper implements Mapper<User> {

    @Override
    public List<User> map(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while(resultSet.next()) {
            users.add(
                    new User(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("age"),
                            resultSet.getString("telephone")
                    )
            );
        }
        return users;
    }

}
