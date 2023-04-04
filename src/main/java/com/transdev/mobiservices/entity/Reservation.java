package com.transdev.mobiservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString
public class Reservation {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private LocalDate date;

        //Une reservation peut comporter plusieurs trajets
        @ManyToOne
        @JoinColumn(name = "bus_id")
        private Bus bus;

        @ManyToOne
        @JoinColumn(name = "client_id")
        private Client client;

        private int seats;
}
