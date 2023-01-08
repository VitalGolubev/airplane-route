package com.vvh.airplaneroute.entitiy;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AirplaneCharacteristic {
    double maxVelocity;
    double maxAcceleration;
    double altitudeChangeRate;
    double bearingChangeRate;

}
