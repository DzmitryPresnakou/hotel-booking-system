package com.presnakov.hotelBookingSystem.dao;

import com.presnakov.hotelBookingSystem.datasourse.ConnectionManager;
import com.presnakov.hotelBookingSystem.entity.User;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UserDao implements Dao<Integer, User> {

    private static final UserDao INSTANCE = new UserDao();

    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
            """;
    private static final String SOFT_DELETE_SQL = """
            UPDATE users
            SET is_active = FALSE
            WHERE id = ?;
            """;
    private static final String SAVE_SQL = """
            INSERT INTO users (first_name, last_name, email, password, user_role_id, is_active)
            VALUES (?, ?, ?, ?, ?, ?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE users
            SET first_name = ?,
                last_name = ?,
                email = ?,
                password = ?,
                user_role_id = ?,
                is_active = ?
            WHERE id = ?;
            """;
    private static final String FIND_ALL_SQL = """
            SELECT users.id,
                   first_name,
                   last_name,
                   email,
                   password,
                   is_active,
                   ur.id,
                   user_role_id,
                   ur.role
            FROM users
            JOIN public.user_role ur
                ON user_role_id = ur.id
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE users.id = ?
            """;
    private static final String GET_BY_EMAIL_AND_PASSWORD_SQL = """
            SELECT users.id,
                   first_name,
                   last_name,
                   email,
                   password,
                   is_active,
                   ur.id,
                   user_role_id,
                   ur.role
            FROM users
            JOIN public.user_role ur
            ON user_role_id = ur.id
            WHERE email = ?
            AND password = ?
            AND is_active = ?
            """;
    private static final String ID = "id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final String USER_ROLE_ID = "user_role_id";
    private static final String IS_ACTIVE = "is_active";
    private static final Boolean IS_ACTIVE_USER = true;

    private final UserRoleDao userRoleDao = UserRoleDao.getInstance();

    private UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @Override
    @SneakyThrows
    public boolean delete(Integer id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setInt(1, id);
            return prepareStatement.executeUpdate() > 0;
        }
    }

    @SneakyThrows
    public boolean softDelete(Integer id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(SOFT_DELETE_SQL)) {
            prepareStatement.setInt(1, id);
            return prepareStatement.executeUpdate() > 0;
        }
    }

    @Override
    @SneakyThrows
    public User save(User user) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, user.getFirstName());
            preparedStatement.setObject(2, user.getLastName());
            preparedStatement.setObject(3, user.getEmail());
            preparedStatement.setObject(4, user.getPassword());
            preparedStatement.setObject(5, user.getUserRole().getId());
            preparedStatement.setObject(6, user.getIsActive());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            user.setId(generatedKeys.getObject(ID, Integer.class));
            return user;
        }
    }

    @Override
    @SneakyThrows
    public void update(User user) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setInt(5, user.getUserRole().getId());
            preparedStatement.setBoolean(6, user.getIsActive());
            preparedStatement.setInt(7, user.getId());

            preparedStatement.executeUpdate();
        }
    }

    @Override
    @SneakyThrows
    public Optional<User> findById(Integer id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        }
    }

    @SneakyThrows
    public Optional<User> findById(Integer id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setInt(1, id);
            var resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }
            return Optional.ofNullable(user);
        }
    }

    @SneakyThrows
    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            preparedStatement.setBoolean(3, IS_ACTIVE_USER);

            var resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }

            return Optional.ofNullable(user);
        }
    }

    @Override
    @SneakyThrows
    public List<User> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }
            return users;
        }
    }

    private User buildUser(ResultSet resultSet) throws java.sql.SQLException {
        return User.builder()
                .id(resultSet.getObject(ID, Integer.class))
                .firstName(resultSet.getObject(FIRST_NAME, String.class))
                .lastName(resultSet.getObject(LAST_NAME, String.class))
                .email(resultSet.getObject(EMAIL, String.class))
                .password(resultSet.getObject(PASSWORD, String.class))
                .userRole(userRoleDao.findById(resultSet.getInt(USER_ROLE_ID),
                        resultSet.getStatement().getConnection()).orElse(null))
                .isActive(resultSet.getObject(IS_ACTIVE, Boolean.class))
                .build();
    }
}