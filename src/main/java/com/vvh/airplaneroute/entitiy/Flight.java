package com.vvh.airplaneroute.entitiy;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class Flight {
    Long number;

    @Builder.Default
    List<WayPoint> wayPoints = new ArrayList<>();

    @Builder.Default
    List<TemporaryPoint> passedPoints = new ArrayList<>();

}
