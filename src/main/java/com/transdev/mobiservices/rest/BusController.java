package com.transdev.mobiservices.rest;

import com.transdev.mobiservices.dao.BusRepository;
import com.transdev.mobiservices.entity.Bus;
import com.transdev.mobiservices.exception.BusNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/buses")
public class BusController {


    private final BusRepository busRepository;

    @Autowired
    public BusController(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @GetMapping
    public Iterable<Bus> getAllBuses() {
        return busRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bus> getBusById(@PathVariable Long id) {
        Optional<Bus> bus = busRepository.findById(id);
        return bus.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Bus createBus(@RequestBody Bus bus) {
        return busRepository.save(bus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        Bus bus = busRepository.findById(id).orElseThrow(() -> new BusNotFoundException(id));
        busRepository.delete(bus);
        return ResponseEntity.noContent().build();
    }
}
