package com.vvh.airplaneroute.repository;

import com.vvh.airplaneroute.entitiy.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FlightRepository extends MongoRepository<Flight, Long> {
}
