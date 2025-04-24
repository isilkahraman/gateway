package com.airline.ticketingapi;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckInRepository extends JpaRepository<CheckIn, Long> {
    Optional<CheckIn> findByTicket_TicketNumber(String ticketNumber);
}
