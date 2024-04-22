package com.presnakov.hotelBookingSystem.dao;

import com.presnakov.hotelBookingSystem.datasourse.ConnectionManager;
import com.presnakov.hotelBookingSystem.entity.UserRole;
import com.presnakov.hotelBookingSystem.entity.UserRoleEnum;
import com.presnakov.hotelBookingSystem.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRoleDao implements Dao<Long, UserRole> {

    public static final UserRoleDao INSTANCE = new UserRoleDao();

    private static final String DELETE_SQL = """
            DELETE FROM user_role
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO user_role (role)
            VALUES (?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE user_role
            SET role = ?
            WHERE id = ?;
            """;

    public static final String FIND_ALL_SQL = """
            SELECT id,
            role,
            FROM user_role
            """;

    public static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    private UserRoleDao() {
    }

    public static UserRoleDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public UserRole save(UserRole userRole) {
        return null;
    }

    @Override
    public void update(UserRole userRole) {

    }

    @Override
    public Optional<UserRole> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("User Role with id %s not found", id), throwables);
        }
    }

    public Optional<UserRole> findById(Long id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            UserRole userRole = null;
            if (resultSet.next()) {
                userRole = buildUserRole(resultSet);
            }
            return Optional.ofNullable(userRole);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Role with id %s not found", id), throwables);
        }
    }

    @Override
    public List<UserRole> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<UserRole> userRoles = new ArrayList<>();
            while (resultSet.next()) {
                userRoles.add(buildUserRole(resultSet));
            }
            return userRoles;
        } catch (SQLException throwables) {
            throw new DaoException("Roles not found", throwables);
        }
    }

    private UserRole buildUserRole(ResultSet resultSet) throws SQLException {
        return new UserRole(
                resultSet.getLong("id"),
                UserRoleEnum.valueOf(resultSet.getObject("role", String.class))
        );
    }
}