package com.presnakov.hotelBookingSystem.dao;

import com.presnakov.hotelBookingSystem.datasourse.ConnectionManager;
import com.presnakov.hotelBookingSystem.entity.PaymentStatus;
import com.presnakov.hotelBookingSystem.entity.PaymentStatusEnum;
import com.presnakov.hotelBookingSystem.exception.DaoException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PaymentStatusDao implements Dao<Long, PaymentStatus> {

    private static final PaymentStatusDao INSTANCE = new PaymentStatusDao();

    private static final String DELETE_SQL = """
            DELETE FROM payment_status
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO payment_status (payment_status)
            VALUES (?);
            """;
    private static final String UPDATE_SQL = """
            UPDATE payment_status
            SET payment_status = ?
             WHERE id = ?;
             """;
    private static final String FIND_ALL_SQL = """
            SELECT id,
            payment_status,
            FROM payment_status
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;
    private static final String ID = "id";
    private static final String PAYMENT_STATUS = "payment_status";

    private PaymentStatusDao() {
    }

    public static PaymentStatusDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setLong(1, id);
            return prepareStatement.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Payment status with id %s not found", id), throwables);
        }
    }

    @Override
    public PaymentStatus save(PaymentStatus paymentStatus) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, String.valueOf(paymentStatus.getPaymentStatusEnum()));
            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                paymentStatus.setId(generatedKeys.getLong(ID));
            }
            return paymentStatus;
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Payment status with id %s not found", paymentStatus.getId()), throwables.getCause());
        }
    }

    @Override
    public void update(PaymentStatus paymentStatus) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, String.valueOf(paymentStatus.getPaymentStatusEnum()));
            preparedStatement.setLong(2, paymentStatus.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Payment status with id %s not found", paymentStatus.getId()), throwables.getCause());
        }
    }

    @Override
    public Optional<PaymentStatus> findById(Long id) {
        try (var connection = ConnectionManager.get()) {
            return findById(id, connection);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Payment Status with id %s not found", id), throwables);
        }
    }

    public Optional<PaymentStatus> findById(Long id, Connection connection) {
        try (var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            PaymentStatus paymentStatus = null;
            if (resultSet.next()) {
                paymentStatus = buildPaymentStatus(resultSet);
            }
            return Optional.ofNullable(paymentStatus);
        } catch (SQLException throwables) {
            throw new DaoException(String.format("Payment Status with id %s not found", id), throwables);
        }
    }

    @Override
    public List<PaymentStatus> findAll() {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<PaymentStatus> paymentStatusses = new ArrayList<>();
            while (resultSet.next()) {
                paymentStatusses.add(buildPaymentStatus(resultSet));
            }
            return paymentStatusses;
        } catch (SQLException throwables) {
            throw new DaoException("Payment Status not found", throwables);
        }
    }

    private PaymentStatus buildPaymentStatus(ResultSet resultSet) throws SQLException {
        return new PaymentStatus(
                resultSet.getLong(ID),
                PaymentStatusEnum.valueOf(resultSet.getObject(PAYMENT_STATUS, String.class))
        );
    }
}