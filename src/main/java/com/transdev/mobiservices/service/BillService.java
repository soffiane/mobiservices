package com.transdev.mobiservices.service;

import com.transdev.mobiservices.entity.Bill;
import com.transdev.mobiservices.entity.Reservation;

import java.util.List;

public interface BillService {
    Bill payReservation(Reservation reservation, Bill bill);

    List<Bill> getSortedBills();
}
