package com.vvh.airplaneroute.entitiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("airplanes")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Airplane {
    @Transient
    public static final String SEQUENCE_NAME = "airplanes_sequence";

    @Id
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Indexed
    Long id;

    AirplaneCharacteristic airplaneCharacteristic;

    TemporaryPoint position;

    @Builder.Default
    List<Flight> flights = new ArrayList<>();

}
