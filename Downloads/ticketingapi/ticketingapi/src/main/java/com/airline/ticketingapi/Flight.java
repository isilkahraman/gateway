package com.airline.ticketingapi;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String flightNumber;
    private String airportFrom;
    private String airportTo;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private Integer duration;
    private Integer capacity;
    private Integer availableSeats;


    public Long getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirportFrom() {
        return airportFrom;
    }

    public String getAirportTo() {
        return airportTo;
    }

    public LocalDateTime getDateFrom() {
        return dateFrom;
    }

    public LocalDateTime getDateTo() {
        return dateTo;
    }

    public Integer getDuration() {
        return duration;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public void setAirportFrom() {
        this.airportFrom = airportFrom;
    }

    public void setAirportTo(String airportTo) {
        this.airportTo = airportTo;
    }

    public void setDateFrom() {
        this.dateFrom = dateFrom;
    }

    public void setDateTo() {
        this.dateTo = dateTo;
    }

    public void setDuration() {
        this.duration = duration;
    }

    public void setCapacity() {
        this.capacity = capacity;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }


}
