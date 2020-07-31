package ru.stb.domain.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.*;
import java.util.*;
import java.util.stream.Stream;

public abstract class AbstractRepository<E> implements Repository<E> {

    private static final String FIND_ALL_SQL_PATTERN = "SELECT * FROM %s";
    private static final String FIND_BY_ID_SQL_PATTERN = "SELECT * FROM %s WHERE %s = ?";
    private static final String INSERT_SQL_PATTERN = "INSERT INTO %s(%s) VALUES (%s)";
    private static final String DELETE_SQL_PATTERN = "DELETE FROM %s WHERE %s = ?";

    private final String jdbcUrl;
    private final String user;
    private final String password;
    private final String driver;
    private final Mapper<E> mapper;

    public AbstractRepository(String jdbcUrl, String user, String password, String driver, Mapper<E> mapper) {
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
        this.driver = driver;
        this.mapper = mapper;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }

    @Override
    public List<E> findAll() {
        registerDriver();
        List<E> resultList = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(getJdbcUrl(), getUser(), getPassword());
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(String.format(FIND_ALL_SQL_PATTERN, getEntityTableName()));
            resultList = mapper.map(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    @Override
    public E findById(Integer id) {
        registerDriver();
        E entity = null;
        try (
                Connection connection = DriverManager.getConnection(getJdbcUrl(), getUser(), getPassword());
                PreparedStatement statement = connection.prepareStatement(
                        String.format(FIND_BY_ID_SQL_PATTERN, getEntityTableName(), getEntityIdFieldName())
                )
        ) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            entity = mapper.map(resultSet)
                    .stream()
                    .findAny()
                    .orElse(null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public boolean update(E entity) {
        return false;
    }

    @Override
    public boolean create(E entity) {
        registerDriver();
        int insertedRowsCount = 0;
        try (
                Connection connection = DriverManager.getConnection(getJdbcUrl(), getUser(), getPassword());
                PreparedStatement statement = generateInsertStatement(connection, entity)
        ) {
            insertedRowsCount = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return insertedRowsCount > 0;
    }

    @Override
    public boolean delete(Integer id) {
        registerDriver();
        int deletedRowsCount = 0;
        try (
                Connection connection = DriverManager.getConnection(getJdbcUrl(), getUser(), getPassword());
                PreparedStatement statement = connection.prepareStatement(
                        String.format(DELETE_SQL_PATTERN, getEntityTableName(), getEntityIdFieldName())
                )
        ) {
            statement.setInt(1, id);
            deletedRowsCount = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deletedRowsCount > 0;
    }

    @SuppressWarnings("unchecked")
    private String getEntityTableName() {
        Class<E> entityClass = (Class) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getDeclaredAnnotation(Table.class);
            return table.value();
        }
        throw new RuntimeException("Entity table name is present");
    }

    @SuppressWarnings("unchecked")
    private String getEntityIdFieldName() {
        Class<E> entityClass = (Class) (((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        return Stream.of(entityClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .map(field -> field.getDeclaredAnnotation(Id.class))
                .map(Id::value)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Entity id field not present"));
    }

    private void registerDriver() {
        try {
            Class.forName(getDriver());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("H2 driver not present");
        }
    }

    @SuppressWarnings("unchecked")
    private PreparedStatement generateInsertStatement(Connection connection, E entity) throws SQLException {
        Class<E> entityClass = (Class<E>) entity.getClass();
        Map<String, Object> columnValueMap = new HashMap<>();
        Arrays.stream(entityClass.getDeclaredFields())
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        if (field.isAnnotationPresent(Id.class)) {
                            columnValueMap.put(field.getAnnotation(Id.class).value(), field.get(entity));
                        }
                        else if (field.isAnnotationPresent(Column.class)) {
                            columnValueMap.put(field.getAnnotation(Column.class).value(), field.get(entity));
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Field not present");
                    }
                });
        List<String> columnNames = new ArrayList<>(columnValueMap.keySet());
        String insertSql = String.format(
                INSERT_SQL_PATTERN,
                getEntityTableName(),
                String.join(", ", columnNames),
                generateInsertParamsTemplate(columnNames.size())
        );
//        System.out.println(insertSql);
        PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
        for (int i = 1; i <= columnNames.size(); ++i) {
            fillPreparedStatementParam(
                    preparedStatement,
                    i,
                    columnValueMap.get(columnNames.get(i - 1))
            );
        }
        return preparedStatement;
    }

    private void fillPreparedStatementParam(
            PreparedStatement preparedStatement,
            Integer index,
            Object value
    ) throws SQLException {
        if (value instanceof Integer) {
            preparedStatement.setInt(index, (Integer) value);
        } else if (value instanceof Long) {
            preparedStatement.setLong(index, (Long) value);
        } else if (value instanceof String) {
            preparedStatement.setString(index, (String) value);
        } else {
            throw new RuntimeException("Entity type " + value.getClass().getSimpleName() + " not support");
        }
    }

    private String generateInsertParamsTemplate(int size) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size - 1; ++i) {
            stringBuilder.append("?").append(", ");
        }
        stringBuilder.append("?");
        return stringBuilder.toString();
    }
}
