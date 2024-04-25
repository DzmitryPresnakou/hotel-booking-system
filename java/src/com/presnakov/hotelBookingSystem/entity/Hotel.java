package com.presnakov.hotelBookingSystem.entity;

import java.util.Objects;

public class Hotel {

    private Long id;
    private String name;

    public Hotel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Hotel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(id, hotel.id) && Objects.equals(name, hotel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Hotel{" +
               "id=" + id +
               ", name='" + name + '\'' +
               '}';
    }
}
