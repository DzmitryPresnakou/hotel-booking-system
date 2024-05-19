package com.presnakov.hotelBookingSystem.validator;

import com.presnakov.hotelBookingSystem.dto.room.CreateRoomDto;

public class CreateRoomValidator implements Validator<CreateRoomDto> {

    private static final CreateRoomValidator INSTANCE = new CreateRoomValidator();

    @Override
    public ValidationResult isValid(CreateRoomDto object) {

        var validationResult = new ValidationResult();

        if (object.getOccupancy() == null) {
            validationResult.add(Error.of("invalid.occupancy", "Occupancy is invalid"));
        }
        return validationResult;
    }

    public static CreateRoomValidator getInstance() {
        return INSTANCE;
    }
}