package com.presnakov.hotelBookingSystem.dto;
import java.util.Objects;

public class RoomDto {

    private final Long id;
    private final String description;

    public RoomDto(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDto roomDto = (RoomDto) o;
        return Objects.equals(id, roomDto.id) && Objects.equals(description, roomDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "RoomDto{" +
               "id=" + id +
               ", description='" + description + '\'' +
               '}';
    }
}