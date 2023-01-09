package com.vvh.airplaneroute.service;

import com.vvh.airplaneroute.entitiy.Flight;

import java.util.List;
import java.util.Optional;

public interface FlightService {

    void save(Flight flight);

    void saveAll(List<Flight> flights);

    Optional<Flight> findById(Long id);

    List<Flight> findAll();

}
