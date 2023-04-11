package com.transdev.mobiservices.rest;

import com.transdev.mobiservices.dao.BillRepository;
import com.transdev.mobiservices.dao.ReservationRepository;
import com.transdev.mobiservices.entity.Bill;
import com.transdev.mobiservices.entity.Reservation;
import com.transdev.mobiservices.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/bills")
public class BillController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillService billService;

    @PostMapping("/pay/{reservationId}")
    public ResponseEntity<Bill> payReservation(@PathVariable Long reservationId, @RequestBody Bill bill) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation with id " + reservationId + " not found"));
        return ResponseEntity.created(URI.create("/bills/" + bill.getId())).body(billService.payReservation(reservation,bill));
    }

    @GetMapping("/sorted")
    public List<Bill> getSortedBills() {
        return billRepository.findAllByOrderByReservationReservationDateDesc();
    }
}
