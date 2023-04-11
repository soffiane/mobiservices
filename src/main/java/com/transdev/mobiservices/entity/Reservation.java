package com.transdev.mobiservices.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_date")
    private LocalDate reservationDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
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
        buses.add(bus);
    }
}
