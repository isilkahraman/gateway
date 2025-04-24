package com.airline.ticketingapi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QueryFlightPassengerListService {

    private final TicketRepository ticketRepository;
    private final CheckInRepository checkInRepository;

    public QueryFlightPassengerListService(TicketRepository ticketRepository,
                                           CheckInRepository checkInRepository) {
        this.ticketRepository = ticketRepository;
        this.checkInRepository = checkInRepository;
    }

    public Page<PassengerSeatDTO> getPassengerList(String flightNumber, LocalDateTime date, Pageable pageable) {

        List<Ticket> tickets = ticketRepository.findByFlight_FlightNumberAndFlight_DateFrom(flightNumber, date);

        List<PassengerSeatDTO> passengerSeatList = tickets.stream().map(ticket -> {
            PassengerSeatDTO dto = new PassengerSeatDTO();
            dto.setPassengerName(ticket.getPassenger().getName());

            checkInRepository.findByTicket_TicketNumber(ticket.getTicketNumber()).ifPresentOrElse(
                    checkIn -> dto.setSeatNumber(checkIn.getSeatNumber()),
                    () -> dto.setSeatNumber("Not Checked In")
            );

            return dto;
        }).toList();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), passengerSeatList.size());

        List<PassengerSeatDTO> pageList = passengerSeatList.subList(start, end);

        return new PageImpl<>(pageList, pageable, passengerSeatList.size());
    }
}
