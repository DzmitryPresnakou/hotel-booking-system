package com.presnakov.hotelBookingSystem.dao;

import com.presnakov.hotelBookingSystem.datasourse.ConnectionManager;
import com.presnakov.hotelBookingSystem.entity.UserRole;
import com.presnakov.hotelBookingSystem.entity.UserRoleEnum;
import com.presnakov.hotelBookingSystem.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRoleDao implements Dao<Long, UserRole> {

    private static final UserRoleDao INSTANCE = new UserRoleDao();

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
    private static final String FIND_ALL_SQL = """
            SELECT id,
            role
            FROM user_role
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
    private static final String ID = "id";
    private static final String ROLE = "role";

    private UserRoleDao() {
    }

    public static UserRoleDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setLong(1, id);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Role with id %s not found", id), throwables);
        }
    }

    @Override
    public UserRole save(UserRole userRole) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, String.valueOf(userRole.getUserRoleEnum()));
            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                userRole.setId(generatedKeys.getLong(ID));
            }
            return userRole;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Role with id %s not found", userRole.getId()), throwables.getCause());
        }
    }

    @Override
    public void update(UserRole userRole) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, String.valueOf(userRole.getUserRoleEnum()));
            preparedStatement.setLong(2, userRole.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Role status with id %s not found", userRole.getId()), throwables.getCause());
        }
    }

    @Override
    public Optional<UserRole> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Role with id %s not found", id), throwables);
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
                resultSet.getLong(ID),
                UserRoleEnum.valueOf(resultSet.getObject(ROLE, String.class))
        );
    }
}