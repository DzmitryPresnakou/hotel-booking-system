package com.presnakov.hotelBookingSystem.validator;

import com.presnakov.hotelBookingSystem.dto.user.CreateUserDto;

import java.util.ResourceBundle;

public class CreateUserValidator implements Validator<CreateUserDto> {

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();


    @Override
    public ValidationResult isValid(CreateUserDto object) {

//        ResourceBundle translations = ResourceBundle.getBundle("translations");

        var validationResult = new ValidationResult();

//        if (object.getEmail().isBlank()) {
//            validationResult.add(Error.of("invalid.email", translations.getString("invalid.email")));
//        }
//        if (object.getFirstName().isBlank()) {
//            validationResult.add(Error.of("invalid.firstName", translations.getString("invalid.firstName")));
//        }
//        if (object.getLastName().isBlank()) {
//            validationResult.add(Error.of("invalid.lastName", translations.getString("invalid.lastName")));
//        }
//        if (object.getPassword().isBlank()) {
//            validationResult.add(Error.of("invalid.password", translations.getString("invalid.password")));
//        }

        if (object.getEmail().isBlank()) {
            validationResult.add(Error.of("invalid.email", "Email is invalid"));
        }
        if (object.getFirstName().isBlank()) {
            validationResult.add(Error.of("invalid.firstName", "First name is invalid"));
        }
        if (object.getLastName().isBlank()) {
            validationResult.add(Error.of("invalid.lastName", "Last name is invalid"));
        }
        if (object.getPassword().isBlank()) {
            validationResult.add(Error.of("invalid.password", "Password is invalid"));
        }
        return validationResult;
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }
}