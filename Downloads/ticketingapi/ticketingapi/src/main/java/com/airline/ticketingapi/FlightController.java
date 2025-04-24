package com.airline.ticketingapi;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/flights")

public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> addFlight(@RequestBody FlightDTO flightDTO) {
        boolean success = flightService.addFlight(flightDTO);
        if(success){
            return ResponseEntity.ok("Flight added successfully.");
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add flight.");
        }
    }

    @GetMapping
    public ResponseEntity<Page<FlightDTO>> queryFlights(
            @RequestParam String airportFrom,
            @RequestParam String airportTo,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            @RequestParam int numberOfPeople,
            @RequestParam boolean roundTrip,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
            ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FlightDTO> result = (Page<FlightDTO>) flightService.queryAvailableFlights(airportFrom, airportTo, dateFrom, dateTo,
                numberOfPeople, roundTrip, pageable);
        return ResponseEntity.ok(result);
    }
}
