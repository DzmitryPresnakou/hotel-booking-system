package com.presnakov.hotelBookingSystem.validator;

public interface Validator<T> {

    ValidationResult isValid(T object);
}