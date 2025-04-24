package com.airline.ticketingapi;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/checkin")

public class CheckInController {
    private final CheckInService checkInService;
    public CheckInController(CheckInService checkInService) {
        this.checkInService = checkInService;
    }



    @PostMapping
    public ResponseEntity<?> checkIn(@RequestBody CheckInRequestDTO request) {

        Optional<CheckInResponseDTO> response = checkInService.assignSeat(
                request.getFlightNumber(),
                request.getDate(),
                request.getPassengerName()
        );

        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ticket not found or already checked in.");
        }
    }
}
