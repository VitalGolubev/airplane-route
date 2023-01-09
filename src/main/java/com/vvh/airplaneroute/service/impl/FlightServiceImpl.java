package com.vvh.airplaneroute.service.impl;

import com.vvh.airplaneroute.entitiy.Flight;
import com.vvh.airplaneroute.repository.FlightRepository;
import com.vvh.airplaneroute.service.FlightService;
import com.vvh.airplaneroute.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public void save(Flight flight) {
        if (flight.getId() == null) {
            flight.setId(sequenceGeneratorService.getSequenceNumber(Flight.SEQUENCE_NAME));
        }
        flightRepository.save(flight);
    }

    @Override
    public void saveAll(List<Flight> flights) {
        flights.forEach(this::save);
    }

    @Override
    public Optional<Flight> findById(Long id) {
        return flightRepository.findById(id);
    }

    @Override
    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

}
