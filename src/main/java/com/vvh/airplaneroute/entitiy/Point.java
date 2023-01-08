package com.vvh.airplaneroute.entitiy;

import lombok.Builder;
import lombok.Data;

/**
 * All formulas for distance, bearing, coordinate etc. given from
 * http://www.movable-type.co.uk/scripts/geodesy-library.html
 * and adopted
 */

@Builder
@Data
public class Point {
    double latitude;
    double longitude;
    double altitude;

    private static final double EARTH_EQUATOR_RADIUS = 6378137;

    public static double getBearingBetweenPoints(Point fromPoint, Point toPoint) {
        double radFromLatitude = Math.toRadians(fromPoint.getLatitude());
        double radToLatitude = Math.toRadians(toPoint.getLatitude());
        double deltaLongitude = Math.toRadians(toPoint.getLongitude()) - Math.toRadians(fromPoint.getLongitude());

        double y = Math.sin(deltaLongitude) * Math.cos(radToLatitude);
        double x = Math.cos(radFromLatitude) * Math.sin(radToLatitude) -
                Math.sin(radFromLatitude) * Math.cos(radToLatitude) * Math.cos(deltaLongitude);

        double bearing = Math.toDegrees(Math.atan2(y, x));
        bearing = (bearing + 360) % 360;
        if (bearing > 180) {
            bearing -= 360;
        }

        return bearing;
    }

    public static double getDistanceBetweenPoints(Point fromPoint, Point toPoint) {
        double radFromLatitude = Math.toRadians(fromPoint.getLatitude());
        double radToLatitude = Math.toRadians(toPoint.getLatitude());
        double deltaLatitude = radFromLatitude - radToLatitude;
        double deltaLongitude = Math.toRadians(fromPoint.getLongitude()) - Math.toRadians(toPoint.getLongitude());
        double s = 2.0d * Math.asin(Math.sqrt(
                Math.pow(Math.sin(deltaLatitude / 2), 2)
                        + Math.cos(radFromLatitude) * Math.cos(radToLatitude) * Math.pow(Math.sin(deltaLongitude / 2), 2)));
        s = s * (EARTH_EQUATOR_RADIUS + (fromPoint.getAltitude() + toPoint.getAltitude()) / 2);
        return s;
    }

    public static Point getPointAtDistanceBearingAltitude(Point fromPoint, double distance, double bearing, double altitude) {
        double toLatitude = Math.toDegrees(
                Math.asin(
                        Math.sin(Math.toRadians(fromPoint.getLatitude()))
                                * Math.cos((distance) / (EARTH_EQUATOR_RADIUS + altitude))
                                + Math.cos(Math.toRadians(fromPoint.getLatitude()))
                                * Math.sin((distance) / (EARTH_EQUATOR_RADIUS + altitude))
                                * Math.cos(Math.toRadians(bearing))));

        double toLongitude = Math.toDegrees(
                Math.toRadians(fromPoint.getLongitude())
                        + Math.atan2(
                        Math.sin(Math.toRadians(bearing))
                                * Math.sin((distance) / (EARTH_EQUATOR_RADIUS + altitude))
                                * Math.cos(Math.toRadians(fromPoint.getLatitude()))
                        , Math.cos((distance) / (EARTH_EQUATOR_RADIUS + altitude))
                                - Math.sin(Math.toRadians(fromPoint.getLatitude()))
                                * Math.sin(Math.toRadians(toLatitude))));

        return Point.builder()
                .latitude(toLatitude)
                .longitude(toLongitude)
                .altitude(altitude)
                .build();
    }

}
