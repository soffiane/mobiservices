package com.transdev.mobiservices.dao;

import com.transdev.mobiservices.entity.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation,Long> {
}
