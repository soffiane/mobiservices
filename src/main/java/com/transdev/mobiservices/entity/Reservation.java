package com.transdev.mobiservices.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@ToString
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "bus_reservation",
            joinColumns = @JoinColumn(name = "reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "bus_id")
    )
    private Set<Bus> buses;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public void addBus(Bus bus) {
        if (buses == null) {
            buses = new HashSet<>();
        }
        if(buses.contains(bus)){
            buses.remove(bus);
        }
        buses.add(bus);
    }
}
