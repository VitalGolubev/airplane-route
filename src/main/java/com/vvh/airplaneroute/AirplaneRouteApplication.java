package com.vvh.airplaneroute;

import com.vvh.airplaneroute.entitiy.Airplane;
import com.vvh.airplaneroute.entitiy.Point;
import com.vvh.airplaneroute.entitiy.WayPoint;
import com.vvh.airplaneroute.service.AirplaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
public class AirplaneRouteApplication implements CommandLineRunner {

    @Autowired
    private AirplaneService airplaneService;

    public static void main(String[] args) {
        SpringApplication.run(AirplaneRouteApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        List<WayPoint> wayPoints = new ArrayList<>();
        wayPoints.add(WayPoint.builder()
                .point(Point.builder()
                        .latitude(50.495421949787136)
                        .longitude(30.546126545139465)
                        .altitude(300)
                        .build())
                .velocity(200).build());
        wayPoints.add(WayPoint.builder()
                .point(Point.builder()
                        .latitude(50.5051398073803)
                        .longitude(30.53943175213319)
                        .altitude(10)
                        .build())
                .velocity(100).build());
        wayPoints.add(WayPoint.builder()
                .point(Point.builder()
                        .latitude(50.48712197176567)
                        .longitude(30.521407309423985)
                        .altitude(10)
                        .build())
                .velocity(100).build());

        List<Airplane> airplanes = airplaneService.findAll();
        String formatAirplane = """
                Airplane with ID: %d\n
                Airplane characterisitics: %s,
                Number of Flights=%d
                Current position:%s""";

        for (Airplane airplane : airplanes) {
            airplaneService.sendToFlight(airplane, wayPoints);
            System.out.printf(formatAirplane,
                    airplane.getId(),
                    airplane.getAirplaneCharacteristic(),
                    airplane.getFlights().size(),
                    airplane.getPosition());
        }
    }

}
