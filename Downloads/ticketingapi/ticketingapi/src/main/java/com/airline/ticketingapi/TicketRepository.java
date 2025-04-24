package com.airline.ticketingapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByFlight_FlightNumberAndFlight_DateFrom(String flightNumber, LocalDateTime date);
}
