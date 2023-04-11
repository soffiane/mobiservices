package com.transdev.mobiservices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transdev.mobiservices.dao.BusRepository;
import com.transdev.mobiservices.dao.ClientRepository;
import com.transdev.mobiservices.entity.Bus;
import com.transdev.mobiservices.entity.Client;
import com.transdev.mobiservices.entity.Reservation;
import com.transdev.mobiservices.exception.InsufficentSeatsException;
import com.transdev.mobiservices.service.ReservationService;
import org.junit.Before;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @Order(1)
    public void shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @Order(2)
    public void shouldCreateReservation() throws Exception {
        Optional<Client> client = clientRepository.findById(1L);
        Optional<Bus> bus = busRepository.findById(1L);

        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDate.now());
        reservation.setClient(client.get());
        reservation.addBus(bus.get());

        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.reservationDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.buses[0].id").value(1l))
                .andExpect(jsonPath("$.buses[0].route").value("Saint-Léger - Coulommiers"))
                .andExpect(jsonPath("$.buses[0].seats").value(4))
                .andExpect(jsonPath("$.buses[0].departureTime").value("10:00"))
                .andExpect(jsonPath("$.buses[0].price").value(4.0))
                .andExpect(jsonPath("$.client.id").value(1l))
                .andExpect(jsonPath("$.client.name").value("Soffiane Boudissa"))
                .andExpect(jsonPath("$.client.email").value("soffiane.boudissa@gmail.com"));
    }

    @Test
    @Order(3)
    public void shouldUpdateReservation() throws Exception {
        Reservation reservation = reservationService.findById(1L);
        Optional<Bus> bus = busRepository.findById(2L);
        Bus storedBus = bus.get();
        reservation.addBus(storedBus);

        Optional<Bus> firstBus = busRepository.findById(1L);
        if (firstBus.isPresent()) {
            Bus firstStoredBus = firstBus.get();
            firstStoredBus.setSeats(firstStoredBus.getSeats() + 1);
            busRepository.save(firstStoredBus);
            reservation.getBuses().stream()
                    .filter(bus1 -> Objects.equals(bus1.getId(), firstStoredBus.getId()))
                    .findFirst()
                    .ifPresent(bus1 -> bus1.setSeats(firstStoredBus.getSeats()));
        }

        mockMvc.perform(put("/reservations/" + reservation.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(reservation.getId()))
                .andExpect(jsonPath("$.reservationDate").value(LocalDate.now().toString()))

                .andExpect(jsonPath("$.buses[0].id").value(1l))
                .andExpect(jsonPath("$.buses[0].route").value("Saint-Léger - Coulommiers"))
                .andExpect(jsonPath("$.buses[0].seats").value(5))
                .andExpect(jsonPath("$.buses[0].departureTime").value("10:00"))
                .andExpect(jsonPath("$.buses[0].price").value(4.0))

                .andExpect(jsonPath("$.buses[1].id").value(2l))
                .andExpect(jsonPath("$.buses[1].route").value("Creteil - Servon"))
                .andExpect(jsonPath("$.buses[1].seats").value(7))
                .andExpect(jsonPath("$.buses[1].departureTime").value("09:30"))
                .andExpect(jsonPath("$.buses[1].price").value(6.5))

                .andExpect(jsonPath("$.client.id").value(1l))
                .andExpect(jsonPath("$.client.name").value("Soffiane Boudissa"))
                .andExpect(jsonPath("$.client.email").value("soffiane.boudissa@gmail.com"));
    }

    @Test
    @Order(4)
    public void shouldThrowException() throws Exception {
        Optional<Client> client = clientRepository.findById(1L);
        Optional<Client> otherClient = clientRepository.findById(2L);
        Optional<Bus> bus = busRepository.findById(4L);

        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDate.now());
        reservation.setClient(client.get());
        reservation.addBus(bus.get());

        reservationService.createReservation(reservation);

        Reservation otherReservation = new Reservation();
        otherReservation.setReservationDate(LocalDate.now());
        otherReservation.setClient(otherClient.get());
        otherReservation.addBus(bus.get());


        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InsufficentSeatsException))
                .andExpect(result -> assertEquals("No more seats available for bus 4", result.getResolvedException().getMessage()));
    }

    @Test()
    @Order(5)
    public void shouldDeleteReservation() throws Exception {
        Reservation reservation = reservationService.findById(1L);

        mockMvc.perform(delete("/reservations/{id}", reservation.getId()))
                .andExpect(status().isNoContent());

        assertThrows(ResourceNotFoundException.class, () -> reservationService.findById(reservation.getId()));
    }

    @Test
    @Order(6)
    public void shouldThrowExceptionWhenReservationNotFound() throws Exception {
        mockMvc.perform(get("/reservations/"+5)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(result -> assertEquals("Could not found reservation with id 5", result.getResolvedException().getMessage()));
    }
}
