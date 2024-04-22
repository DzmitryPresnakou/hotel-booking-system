package com.presnakov.hotelBookingSystem.dao;

import com.presnakov.hotelBookingSystem.datasourse.ConnectionManager;
import com.presnakov.hotelBookingSystem.entity.User;
import com.presnakov.hotelBookingSystem.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<Long, User> {

    private static final UserDao INSTANCE = new UserDao();

    private static final String DELETE_SQL = """
            DELETE FROM users
            WHERE id = ?
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
                   ur.role
            FROM users
            JOIN public.user_role ur
                ON user_role_id = ur.id
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE users.id = ?
            """;

    private final UserRoleDao userRoleDao = UserRoleDao.getInstance();

    private UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setLong(1, id);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("User with id %s not found", id), throwables);
        }
    }

    @Override
    public User save(User user) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setLong(5, user.getUserRole().getId());
            preparedStatement.setBoolean(6, user.getActive());

            preparedStatement.executeUpdate();
            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                user.setId(generatedKeys.getLong("id"));
            }
            return user;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("User with id %s not found", user.getId()), throwables.getCause());
        }
    }

    @Override
    public void update(User user) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setLong(5, user.getUserRole().getId());
            preparedStatement.setBoolean(6, user.getActive());
            preparedStatement.setLong(7, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(String.format("User with id %s not found", user.getId()), throwables.getCause());
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Room Role with id %s not found", id), throwables);
        }
    }

    public Optional<User> findById(Long id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }
            return Optional.ofNullable(user);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("User with id %s not found", id), throwables);
        }
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getString("password"),
                userRoleDao.findById(resultSet.getLong("user_role_id"),
                        resultSet.getStatement().getConnection()).orElse(null),
                resultSet.getBoolean("is_active")
        );
    }

    @Override
    public List<User> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }
            return users;
        } catch (SQLException throwables) {
            throw new DaoException("Users not found", throwables);
        }
    }
}