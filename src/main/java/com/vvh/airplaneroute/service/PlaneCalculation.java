package com.vvh.airplaneroute.service;

import com.vvh.airplaneroute.entitiy.AirplaneCharacteristic;
import com.vvh.airplaneroute.entitiy.Point;
import com.vvh.airplaneroute.entitiy.TemporaryPoint;
import com.vvh.airplaneroute.entitiy.WayPoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlaneCalculation {
    private static final double SAMPLING = 1.0d;

    public static List<TemporaryPoint> calculateRoute(AirplaneCharacteristic airplaneCharacteristic, List<WayPoint> wayPoints) {
        if (wayPoints.size() <= 1) {
            throw new IllegalArgumentException("Count of Way points must be greater than one");
        }

        List<TemporaryPoint> temporaryPoints = new ArrayList<>();
        WayPoint startPoint = wayPoints.get(0);
        TemporaryPoint currentPoint = TemporaryPoint.fromWayPoint(startPoint);
        currentPoint.setBearing(Point.getBearingBetweenPoints(startPoint.getPoint(), wayPoints.get(1).getPoint()));

        temporaryPoints.add(currentPoint);

        for (Iterator<WayPoint> i = wayPoints.listIterator(1); i.hasNext(); ) {
            WayPoint endPoint = i.next();
            while (currentPoint.getPoint().getLatitude() != endPoint.getPoint().getLatitude()
                    && currentPoint.getPoint().getLongitude() != endPoint.getPoint().getLongitude()) {
                TemporaryPoint nextPoint = getNextPoint(airplaneCharacteristic, currentPoint, endPoint);
                temporaryPoints.add(nextPoint);
                currentPoint = nextPoint;
            }
        }
        return temporaryPoints;
    }

    private static TemporaryPoint getNextPoint(AirplaneCharacteristic airplaneCharacteristic,
                                               TemporaryPoint fromPoint,
                                               WayPoint toPoint) {
        double velocity = getChangedParameter(fromPoint.getVelocity()
                , toPoint.getVelocity()
                , airplaneCharacteristic.getMaxAcceleration());
        double altitude = getChangedParameter(fromPoint.getPoint().getAltitude()
                , toPoint.getPoint().getAltitude()
                , airplaneCharacteristic.getAltitudeChangeRate());

        double bearing = getOptimalChangedBearing(fromPoint.getBearing()
                , Point.getBearingBetweenPoints(fromPoint.getPoint(), toPoint.getPoint())
                , airplaneCharacteristic.getBearingChangeRate());

        double distance = Point.getDistanceBetweenPoints(fromPoint.getPoint(), toPoint.getPoint());
        if (distance < velocity) {
            TemporaryPoint temporaryPoint = TemporaryPoint.fromWayPoint(toPoint);
            temporaryPoint.setBearing(bearing);
            temporaryPoint.setVelocity(velocity);
        }

        Point point = distance > velocity * SAMPLING ?
                Point.getPointAtDistanceBearingAltitude(fromPoint.getPoint()
                        , velocity * SAMPLING
                        , bearing
                        , altitude)
                :
                Point.builder()
                        .longitude(toPoint.getPoint().getLongitude())
                        .latitude(toPoint.getPoint().getLatitude())
                        .altitude(toPoint.getPoint().getAltitude())
                        .build();

        return TemporaryPoint.builder()
                .point(point)
                .velocity(velocity)
                .bearing(bearing)
                .build();
    }

    private static double getChangedParameter(double actualParameter,
                                              double requestedParameter,
                                              double changeRate) {
        double newParameter = actualParameter;
        if (actualParameter > requestedParameter) {
            newParameter -= changeRate;
            if (newParameter < requestedParameter) {
                newParameter = requestedParameter;
            }
        } else if (actualParameter < requestedParameter) {
            newParameter += changeRate;
            if (newParameter > requestedParameter) {
                newParameter = requestedParameter;
            }
        }
        return newParameter;
    }

    private static double getOptimalChangedBearing(double actualBearing, double requestedBearing, double changeRate) {
        if (Math.abs(actualBearing - requestedBearing) > 180) {
            if (actualBearing > 0) {
                requestedBearing = (requestedBearing + 360) % 360;
            } else {
                actualBearing = (actualBearing + 360) % 360;
            }
        }

        return getChangedParameter(actualBearing, requestedBearing, changeRate);
    }

}
