package com.airline.ticketingapi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/passengers")

public class QueryFlightPassengerListController {
    private final QueryFlightPassengerListService passengerListService;

    public QueryFlightPassengerListController (QueryFlightPassengerListService passengerListService) {
        this.passengerListService = passengerListService;
    }

    //Auth Required
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<PassengerSeatDTO>> getPassengerList(
            @RequestParam String flightNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<PassengerSeatDTO> result = passengerListService.getPassengerList(flightNumber, date, pageable);
        return ResponseEntity.ok(result);
    }
}
