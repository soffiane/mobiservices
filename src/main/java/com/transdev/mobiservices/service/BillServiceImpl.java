package com.transdev.mobiservices.service;

import com.transdev.mobiservices.dao.BillRepository;
import com.transdev.mobiservices.entity.Bill;
import com.transdev.mobiservices.entity.Bus;
import com.transdev.mobiservices.entity.Reservation;
import com.transdev.mobiservices.entity.enums.PaymentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;

    @Autowired
    public BillServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Transactional
    public Bill payReservation(Reservation reservation, Bill bill) {
        double totalPrice = reservation.getBuses().stream().mapToDouble(Bus::getPrice).sum();
        if (totalPrice > 100) {
            totalPrice *= 0.95; // 5% discount
        }
        bill.setTotalPrice(totalPrice);
        bill.setReservation(reservation);
        return billRepository.save(bill);
    }
    public List<Bill> getSortedBills() {
        return billRepository.findAllByOrderByReservationReservationDateDesc();
    }

}
