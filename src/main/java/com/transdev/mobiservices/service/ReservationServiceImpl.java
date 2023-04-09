package com.transdev.mobiservices.service;

import com.transdev.mobiservices.dao.BusRepository;
import com.transdev.mobiservices.dao.ReservationRepository;
import com.transdev.mobiservices.entity.Reservation;
import com.transdev.mobiservices.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationServiceImpl implements ReservationService{

    private ReservationRepository reservationRepository;

    private BusRepository busRepository;

    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository, BusRepository busRepository) {
        this.reservationRepository = reservationRepository;
        this.busRepository = busRepository;
    }

    public Iterable<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation findById(Long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not found reservation with id " + id));
    }

    @Override
    @Transactional
    public Reservation createReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    @Transactional
    public Reservation updateReservation(Reservation reservation, Long reservationId) {
        Reservation existingReservation = reservationRepository.findById(reservation.getId()).orElseThrow(() -> new ResourceNotFoundException("Could not find existing reservation with id :"+reservation.getId()));
        existingReservation.getBuses().addAll(reservation.getBuses());
        existingReservation.setClient(reservation.getClient());
        existingReservation.setReservationDate(reservation.getReservationDate());
        return reservationRepository.save(existingReservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not found reservation with id " + id));
        reservationRepository.delete(reservation);
    }
}
