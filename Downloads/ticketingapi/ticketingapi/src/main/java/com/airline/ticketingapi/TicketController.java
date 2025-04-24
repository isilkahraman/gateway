package com.airline.ticketingapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping

public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // 🧾 Buy Ticket — Authentication Required
    @PostMapping
    @PreAuthorize("isAuthenticated()") // JWT protection
    public ResponseEntity<?> buyTicket(@RequestBody TicketPurchaseRequestDTO request) {

        List<TicketResponseDTO> tickets = ticketService.purchaseTickets(
                request.getFlightId(),
                request.getPassengerNames()
        );

        if (tickets.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Flight is sold out or insufficient seats.");
        }
        return ResponseEntity.ok(tickets);
    }
}
