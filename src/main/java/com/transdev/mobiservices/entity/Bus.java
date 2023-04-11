package com.transdev.mobiservices.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String route;

    @ManyToMany(mappedBy = "buses")
    @JsonIgnore
    private Set<Reservation> reservations;

    private int seats;

    @Column(name = "departure_time")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime departureTime;

    private double price;

}
