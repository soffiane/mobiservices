package com.transdev.mobiservices.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToMany(mappedBy = "buses")
//    private Set<Reservation> reservations;

    private String route;

    private int seats;

    @Column(name = "departure_time")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime departureTime;

    private Double price;
}
