package com.transdev.mobiservices.rest;

import com.transdev.mobiservices.dao.BillRepository;
import com.transdev.mobiservices.dao.ReservationRepository;
import com.transdev.mobiservices.entity.Bill;
import com.transdev.mobiservices.entity.Reservation;
import com.transdev.mobiservices.exception.ReservationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BillRepository billRepository;

    @PostMapping("/pay/{reservationId}")
    public ResponseEntity<Void> payReservation(@PathVariable Long reservationId, @RequestBody Bill bill) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation with id " + reservationId + " not found"));
        bill.setReservation(reservation);
        billRepository.save(bill);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/sorted")
    public List<Bill> getSortedBills() {
        return billRepository.findAllByOrderByReservationReservationDateDesc();
    }
}
