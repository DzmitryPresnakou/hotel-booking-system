package com.presnakov.hotelBookingSystem.entity;import java.math.BigDecimal;
import java.util.Objects;

public class RoomClass {

    private Long id;
    private RoomClassEnum comfortClass;
    private BigDecimal pricePerDay;

    public RoomClass(Long id, RoomClassEnum comfortClass, BigDecimal pricePerDay) {
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

    public RoomClassEnum getComfortClass() {
        return comfortClass;
    }

    public void setComfortClass(RoomClassEnum comfortClass) {
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
        return Objects.equals(id, roomClass.id) && comfortClass == roomClass.comfortClass && Objects.equals(pricePerDay, roomClass.pricePerDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, comfortClass, pricePerDay);
    }

    @Override
    public String toString() {
        return "RoomClass{" +
               "id=" + id +
               ", comfortClass=" + comfortClass +
               ", pricePerDay=" + pricePerDay +
               '}';
    }
}