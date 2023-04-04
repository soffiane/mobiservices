package com.transdev.mobiservices.dao;

import com.transdev.mobiservices.entity.Bill;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BillRepository extends CrudRepository<Bill, Long> {
    List<Bill> findAllByOrderByReservationDateDesc();
}
