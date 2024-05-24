package com.presnakov.hotelBookingSystem.service;

import com.presnakov.hotelBookingSystem.dao.HotelDao;
import com.presnakov.hotelBookingSystem.dto.hotel.HotelDto;
import com.presnakov.hotelBookingSystem.entity.Hotel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HotelService {

    private static HotelService INSTANCE = new HotelService();

    private final HotelDao hotelDao = HotelDao.getInstance();

    public List<HotelDto> findAll() {
        return hotelDao.findAll().stream()
                .map(hotel -> HotelDto.builder()
                        .id(hotel.getId())
                        .name(hotel.getName())
                        .build())
                .collect(toList());
    }

    public HotelDto findById(Integer hotelId) {
        Optional<Hotel> hotel = hotelDao.findById(hotelId);
        return HotelDto.builder()
                .id(hotel.get().getId())
                .name(hotel.get().getName())
                .build();
    }

    public HotelDto findByName(String name) {
        Optional<Hotel> hotel = hotelDao.findByName(name);
        return HotelDto.builder()
                .id(hotel.get().getId())
                .name(hotel.get().getName())
                .build();
    }

    public static HotelService getInstance() {
        return INSTANCE;
    }
}