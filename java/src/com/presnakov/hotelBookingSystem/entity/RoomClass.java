package com.presnakov.hotelBookingSystem.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class RoomClass {

    private Long id;
    private String comfortClass;
    private BigDecimal pricePerDay;

    public RoomClass(Long id, String comfortClass, BigDecimal pricePerDay) {
        this.id = id;
        this.comfortClass = comfortClass;
        this.pricePerDay = pricePerDay;
    }

    public RoomClass() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComfortClass() {
        return comfortClass;
    }

    public void setComfortClass(String comfortClass) {
        this.comfortClass = comfortClass;
    }

    public BigDecimal getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(BigDecimal pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomClass roomClass = (RoomClass) o;
        return Objects.equals(id, roomClass.id) && Objects.equals(comfortClass, roomClass.comfortClass) && Objects.equals(pricePerDay, roomClass.pricePerDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comfortClass, pricePerDay);
    }

    @Override
    public String toString() {
        return "RoomClass{" +
               "id=" + id +
               ", comfortClass='" + comfortClass + '\'' +
               ", pricePerDay=" + pricePerDay +
               '}';
    }
}