package com.presnakov.hotelBookingSystem.validator;

import com.presnakov.hotelBookingSystem.datasourse.LocalDateFormatter;
import com.presnakov.hotelBookingSystem.dto.order.CreateOrderDto;

import java.time.LocalDate;

public class CreateRoomOrderValidator implements Validator<CreateOrderDto> {

    private static final CreateRoomOrderValidator INSTANCE = new CreateRoomOrderValidator();

    @Override
    public ValidationResult isValid(CreateOrderDto object) {

        var validationResult = new ValidationResult();
        if (object.getCheckInDate().isEmpty()) {
            validationResult.add(Error.of("not.choose.check.in.date", "Choose check in date"));
        } else if (object.getCheckOutDate().isEmpty()) {
            validationResult.add(Error.of("not.choose.check.out.date", "Choose check out date"));
        } else if (LocalDateFormatter.format(object.getCheckInDate()).isBefore(LocalDate.now())) {
            validationResult.add(Error.of("invalid.check.in.date", "Invalid check in date"));
        } else if (LocalDateFormatter.format(object.getCheckOutDate()).isBefore(LocalDate.now())
                   || LocalDateFormatter.format(object.getCheckOutDate()).isBefore(LocalDateFormatter.format(object.getCheckInDate()))
                   || LocalDateFormatter.format(object.getCheckOutDate()).isEqual(LocalDateFormatter.format(object.getCheckInDate()))) {
            validationResult.add(Error.of("invalid.check.out.date", "Invalid check out date"));
        }
        return validationResult;
    }

    public static CreateRoomOrderValidator getInstance() {
        return INSTANCE;
    }
}