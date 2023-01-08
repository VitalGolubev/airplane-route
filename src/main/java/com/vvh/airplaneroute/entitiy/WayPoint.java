package com.vvh.airplaneroute.entitiy;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;


@Builder
@Data
public class WayPoint {
    Point point;
    double velocity;

    public static WayPoint fromTemporaryPoint(TemporaryPoint temporaryPoint) {
        return WayPoint.builder()
                .point(temporaryPoint.getPoint())
                .velocity(temporaryPoint.getVelocity())
                .build();
    }

}
