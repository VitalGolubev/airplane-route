package com.vvh.airplaneroute.service;

import com.vvh.airplaneroute.entitiy.Airplane;
import com.vvh.airplaneroute.entitiy.WayPoint;

import java.awt.desktop.OpenFilesEvent;
import java.util.List;
import java.util.Optional;

public interface AirplaneService {

    void sendToFlight(Airplane airplane, List<WayPoint> wayPoints);

    void save(Airplane airplane);

    List<Airplane> findAll();

    Optional<Airplane> findById(Long id);

}
