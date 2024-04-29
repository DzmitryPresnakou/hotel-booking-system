package com.presnakov.hotelBookingSystem.validator;

import com.presnakov.hotelBookingSystem.datasourse.LocalDateFormatter;
import com.presnakov.hotelBookingSystem.dto.user.CreateUserDto;
import com.presnakov.hotelBookingSystem.entity.UserRoleEnum;

public class CreateUserValidator implements Validator<CreateUserDto> {

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();


    @Override
    public ValidationResult isValid(CreateUserDto object) {
        var validationResult = new ValidationResult();
//        if (UserRoleEnum.valueOf(object.getUserRole()) == null) {
//            validationResult.add(Error.of("invalid.role", "Role is invalid"));
//        }
        if (object.getEmail() == null) {
            validationResult.add(Error.of("invalid.email", "Email is invalid"));
        }
        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}