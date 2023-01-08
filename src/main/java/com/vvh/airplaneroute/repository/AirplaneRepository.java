package com.vvh.airplaneroute.repository;

import com.vvh.airplaneroute.entitiy.Airplane;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AirplaneRepository extends MongoRepository<Airplane, Long> {
}