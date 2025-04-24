package com.airline.ticketingapi;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import java.time.LocalDateTime;

@Service

public class FlightService {

    private final FlightRepository flightRepository;
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }
    Flight flight = new Flight();
    public boolean addFlight(FlightDTO flightDTO) {

        flight.setFlightNumber(flightDTO.getFlightNumber());
        flight.setAirportFrom();
        flight.getAirportTo();
        flight.setDateFrom();
        flight.setDateTo();
        flight.setDuration();
        flight.setCapacity();
        flight.setAvailableSeats(flightDTO.getCapacity());
        flightRepository.save(flight);
        return true;
    }

    public Page queryAvailableFlights(String airportFrom, String airportTo, LocalDateTime dateFrom,
                                      LocalDateTime dateTo, int numberOfPeople, boolean roundTrip,
                                      Pageable pageable) {
        return flightRepository.findByParams(airportFrom, airportTo, dateFrom, dateTo, numberOfPeople, pageable).map(flight -> {
                    FlightDTO dto = new FlightDTO();
                    dto.setFlightNumber(flight.getFlightNumber());
                    dto.setAirportFrom(flight.getAirportFrom());
                    dto.setAirportTo(flight.getAirportTo());
                    dto.setDateFrom(flight.getDateFrom());
                    dto.setDateTo(flight.getDateTo());
                    dto.setDuration(flight.getDuration());
                    dto.setCapacity(flight.getCapacity());
                    return dto;
                });
    }
}
