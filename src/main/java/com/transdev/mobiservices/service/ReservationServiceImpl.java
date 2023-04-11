package com.transdev.mobiservices.service;

import com.transdev.mobiservices.dao.BusRepository;
import com.transdev.mobiservices.dao.ReservationRepository;
import com.transdev.mobiservices.entity.Bus;
import com.transdev.mobiservices.entity.Reservation;
import com.transdev.mobiservices.exception.InsufficentSeatsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ReservationServiceImpl implements ReservationService {

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
        Set<Bus> buses = updateSeats(reservation.getBuses(), -1);
        reservation.setBuses(buses);
        return reservationRepository.save(reservation);
    }

    private Set<Bus> updateSeats(Set<Bus> reservationBuses, int seat) {
        List<Bus> buses = List.copyOf(reservationBuses);
        Set<Bus> result = new HashSet<>();
        for (Bus bus : buses) {
            Bus storedBus = busRepository.findById(bus.getId()).orElseThrow(() -> new ResourceNotFoundException("Could not found bus with id " + bus.getId()));
            if (storedBus.getSeats() + seat < 0) {
                throw new InsufficentSeatsException("No more seats available for bus " + bus.getId());
            }
            storedBus.setSeats(storedBus.getSeats() + seat);
            busRepository.save(storedBus);
            result.add(storedBus);
        }
        return result;
    }

    @Override
    @Transactional
    public Reservation updateReservation(Reservation reservation, Long reservationId) {
        //recuperer la reservation existante et la comparer avec celle passée en parametre
        Reservation existingReservation = reservationRepository.findById(reservationId).orElseThrow(() -> new ResourceNotFoundException("Could not find existing reservation with id :" + reservationId));

        Set<Bus> originalBuses = new HashSet<>(existingReservation.getBuses());
        Set<Bus> newBuses = new HashSet<>(reservation.getBuses());

        // Find the buses that were added
        newBuses.removeAll(originalBuses);
        Set<Bus> addedBuses = new HashSet<>(newBuses);

        // Find the buses that were removed
        originalBuses.removeAll(reservation.getBuses());
        Set<Bus> removedBuses = new HashSet<>(originalBuses);

        // Find the buses that were updated
        Set<Bus> updatedBuses = new HashSet<>();
        for (Bus newBus : reservation.getBuses()) {
            Optional<Bus> optionalOriginalBus = existingReservation.getBuses().stream()
                    .filter(originalBus -> originalBus.getId().equals(newBus.getId()))
                    .findFirst();
            if (optionalOriginalBus.isPresent()) {
                Bus originalBus = optionalOriginalBus.get();
                if (!originalBus.equals(newBus)) {
                    updatedBuses.add(newBus);
                    if (newBus.getSeats() < originalBus.getSeats()) {
                        updateSeats(updatedBuses, -1);
                    } else {
                        updateSeats(updatedBuses, 1);
                    }
                }
            }
        }

        existingReservation.getBuses().addAll(updateSeats(addedBuses, -1));
        existingReservation.getBuses().addAll(updateSeats(removedBuses, 1));
        existingReservation.setClient(reservation.getClient());
        existingReservation.setReservationDate(reservation.getReservationDate());
        return reservationRepository.save(existingReservation);
    }

    @Transactional
    public void deleteReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not found reservation with id " + id));
        updateSeats(reservation.getBuses(), 1);
        reservationRepository.delete(reservation);
    }
}
