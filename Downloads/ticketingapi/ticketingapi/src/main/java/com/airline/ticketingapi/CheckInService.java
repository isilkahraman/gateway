package com.airline.ticketingapi;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class CheckInService {
    private final TicketRepository ticketRepository;
    private final CheckInRepository checkInRepository;

    public CheckInService(TicketRepository ticketRepository,
                          CheckInRepository checkInRepository) {
        this.ticketRepository = ticketRepository;
        this.checkInRepository = checkInRepository;
    }

    @Transactional
    public Optional<CheckInResponseDTO> assignSeat(String flightNumber, LocalDateTime date, String passengerName) {

        List<Ticket> tickets = ticketRepository.findByFlight_FlightNumberAndFlight_DateFrom(flightNumber, date);

        // find matching ticket that is not checked in
        for (Ticket ticket : tickets) {
            if (ticket.getPassenger().getName().equalsIgnoreCase(passengerName)) {

                // check if already checked in
                Optional<CheckIn> existingCheckIn = checkInRepository.findByTicket_TicketNumber(ticket.getTicketNumber());
                if (existingCheckIn.isPresent()) {
                    return Optional.empty(); // already checked in
                }

                // assign seat number (simple: ticket ID as seat number)
                CheckIn checkIn = new CheckIn();
                checkIn.setTicket(ticket);
                checkIn.setSeatNumber("Seat-" + ticket.getId());
                checkIn.setStatus("CHECKED_IN");
                checkInRepository.save(checkIn);

                CheckInResponseDTO responseDTO = new CheckInResponseDTO();
                responseDTO.setTicketNumber(ticket.getTicketNumber());
                responseDTO.setSeatNumber(checkIn.getSeatNumber());
                responseDTO.setStatus(checkIn.getStatus());

                return Optional.of(responseDTO);
            }
        }

        return Optional.empty(); // no matching ticket
    }
}
