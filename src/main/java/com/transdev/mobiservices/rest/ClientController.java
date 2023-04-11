package com.transdev.mobiservices.rest;

import com.transdev.mobiservices.dao.ClientRepository;
import com.transdev.mobiservices.entity.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @GetMapping
    public Iterable<Client> getAllReservations() {
        return clientRepository.findAll();
    }
}
