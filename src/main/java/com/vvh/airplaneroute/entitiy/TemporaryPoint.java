package com.vvh.airplaneroute.entitiy;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TemporaryPoint {
    Point point;
    double velocity;
    double bearing;

    public static TemporaryPoint fromWayPoint(WayPoint wayPoint) {
        return TemporaryPoint.builder()
               .point(wayPoint.getPoint())
               .velocity(wayPoint.getVelocity())
               .build();
    }
}
