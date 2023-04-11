package com.transdev.mobiservices.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

@Data
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String route;

    private int seats;

    @Column(name = "departure_time")
    @JsonFormat(pattern="HH:mm")
    private LocalTime departureTime;

    private double price;

}
