package ru.stb.domain.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Mapper<E> {

    List<E> map(ResultSet resultSet) throws SQLException;

}
