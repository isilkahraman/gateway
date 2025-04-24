package com.airline.ticketingapi;

import java.util.List;

public class TicketPurchaseRequestDTO {
    private Long flight_id;
    private List<String> passengerNames;

    public TicketPurchaseRequestDTO(Long flight_id, List<String> passengerNames) {
        this.flight_id = flight_id;
        this.passengerNames = passengerNames;
    }

    public Long getFlightId() {
        return flight_id;
    }

    public void setFlightId(Long flight_id) {
        this.flight_id = flight_id;
    }

    public List<String> getPassengerNames() {
        return passengerNames;
    }

    public void setPassengerNames(List<String> passengerNames) {
        this.passengerNames = passengerNames;
    }
}
