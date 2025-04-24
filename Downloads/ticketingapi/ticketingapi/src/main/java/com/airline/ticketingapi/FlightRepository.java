package com.airline.ticketingapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT f FROM Flight f WHERE f.airportFrom = :from AND f.airportTo = :to "
            + "AND f.dateFrom >= :startDate AND f.dateTo <= :endDate "
            + "AND f.availableSeats >= :numberOfPeople")
    Page<Flight> findByParams(@Param("from") String airportFrom,
                              @Param("to") String airportTo,
                              @Param("startDate") LocalDateTime dateFrom,
                              @Param("endDate") LocalDateTime dateTo,
                              @Param("numberOfPeople") int numberOfPeople,
                              Pageable pageable);
}
