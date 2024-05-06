package com.presnakov.hotelBookingSystem.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
