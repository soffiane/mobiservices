package com.transdev.mobiservices.service;

import com.transdev.mobiservices.entity.Reservation;

public interface ReservationService {
    Iterable<Reservation> getAllReservations();

    Reservation findById(Long id);

    Reservation createReservation(Reservation reservation);

    Reservation updateReservation(Reservation reservation, Long id);

    void deleteReservation(Long id);

}
