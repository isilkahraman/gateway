package com.airline.ticketingapi;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final FlightRepository flightRepository;
    private final PassengerRepository passengerRepository;

    public TicketService(TicketRepository ticketRepository,
                         FlightRepository flightRepository,
                         PassengerRepository passengerRepository) {
        this.ticketRepository = ticketRepository;
        this.flightRepository = flightRepository;
        this.passengerRepository = passengerRepository;
    }

    @Transactional
    public List<TicketResponseDTO> purchaseTickets(Long flightId, List<String> passengerNames) {

        Optional<Flight> optionalFlight = flightRepository.findById(flightId);
        if (optionalFlight.isEmpty()) return Collections.emptyList();

        Flight flight = optionalFlight.get();

        if (flight.getAvailableSeats() < passengerNames.size()) {
            return Collections.emptyList(); // Sold out or not enough seats
        }

        List<TicketResponseDTO> result = new ArrayList<>();

        for (String name : passengerNames) {

            Passenger passenger = new Passenger();
            passenger.setName(name);
            passenger = passengerRepository.save(passenger);

            Ticket ticket = new Ticket();
            ticket.setTicketNumber(UUID.randomUUID().toString());
            ticket.setFlight(flight);
            ticket.setPassenger(passenger);
            ticket.setStatus("PAID");

            ticketRepository.save(ticket);

            // For response
            TicketResponseDTO dto = new TicketResponseDTO();
            dto.setTicketNumber(ticket.getTicketNumber());
            dto.setStatus(ticket.getStatus());
            result.add(dto);
        }

        // Decrease available seats
        flight.setAvailableSeats(flight.getAvailableSeats() - passengerNames.size());
        flightRepository.save(flight);

        return result;
    }
}
