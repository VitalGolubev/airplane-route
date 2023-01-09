package com.vvh.airplaneroute.service.impl;

import com.vvh.airplaneroute.entitiy.Airplane;
import com.vvh.airplaneroute.entitiy.Flight;
import com.vvh.airplaneroute.entitiy.WayPoint;
import com.vvh.airplaneroute.repository.AirplaneRepository;
import com.vvh.airplaneroute.service.AirplaneService;
import com.vvh.airplaneroute.service.FlightService;
import com.vvh.airplaneroute.service.PlaneCalculation;
import com.vvh.airplaneroute.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AirplaneServiceImpl implements AirplaneService {

    @Autowired
    AirplaneRepository airplaneRepository;

    @Autowired
    FlightService flightService;

    @Autowired
    SequenceGeneratorService sequenceGeneratorService;

    @Override
    public void sendToFlight(Airplane airplane, List<WayPoint> wayPoints) {
        List<WayPoint> wayPointsWithAirplaneStartPoint = new ArrayList<>();
        wayPointsWithAirplaneStartPoint.add(WayPoint.fromTemporaryPoint(airplane.getPosition()));
        wayPointsWithAirplaneStartPoint.addAll(wayPoints);
        Flight flight = Flight
                .builder()
                .number((long) airplane.getFlights().size())
                .wayPoints(wayPoints)
                .passedPoints(PlaneCalculation.calculateRoute(airplane.getAirplaneCharacteristic(), wayPointsWithAirplaneStartPoint))
                .build();

        airplane.getFlights().add(flight);
        airplane.setPosition(flight.getPassedPoints().get(flight.getPassedPoints().size() - 1));
        save(airplane);
    }

    @Override
    public void save(Airplane airplane) {
        flightService.saveAll(airplane.getFlights());
        if (airplane.getId() == null) {
            airplane.setId(sequenceGeneratorService.getSequenceNumber(Airplane.SEQUENCE_NAME));
        }
        airplaneRepository.save(airplane);
    }

    @Override
    public List<Airplane> findAll() {
        return airplaneRepository.findAll();
    }

    @Override
    public Optional<Airplane> findById(Long id) {
        return airplaneRepository.findById(id);
    }

}
