package com.transdev.mobiservices.rest;

import com.transdev.mobiservices.dao.BusRepository;
import com.transdev.mobiservices.entity.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
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
    public ResponseEntity<Bus> createBus(@RequestBody Bus bus) {
        return ResponseEntity.created(URI.create("/buses/" + bus.getId())).body(busRepository.save(bus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBus(@PathVariable Long id) {
        Bus bus = busRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Could not find bus with id :" + id));
        busRepository.delete(bus);
        return ResponseEntity.noContent().build();
    }
}
