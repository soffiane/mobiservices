package com.transdev.mobiservices.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.transdev.mobiservices.dao.BusRepository;
import com.transdev.mobiservices.dao.ClientRepository;
import com.transdev.mobiservices.entity.Bill;
import com.transdev.mobiservices.entity.Client;
import com.transdev.mobiservices.entity.Reservation;
import com.transdev.mobiservices.entity.enums.PaymentType;
import com.transdev.mobiservices.service.BillService;
import com.transdev.mobiservices.service.ReservationService;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BillControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private BillService billService;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    @Order(1)
    public void shouldCalculateTotalReservationPrice() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setReservationDate(LocalDate.now());
        Client client = clientRepository.findById(1L).get();
        reservation.setClient(client);
        reservation.addBus(busRepository.findById(1L).get());

        Bill bill = new Bill();
        bill.setPaymentType(PaymentType.PAYPAL);
        bill.setPaymentInformations("soffiane.boudissa@gmail.com");

        Reservation createdReservation = reservationService.createReservation(reservation);

        JSONObject expectedBus = new JSONObject();
        expectedBus.put("id", 1);
        expectedBus.put("route", "Saint-Léger - Coulommiers");
        expectedBus.put("seats", 5);
        expectedBus.put("departureTime", "10:00");
        expectedBus.put("price", 4.0);

        mockMvc.perform(post("/bills/pay/"+createdReservation.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bill)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.reservation.id").value(1))
                .andExpect(jsonPath("$.reservation.reservationDate").value("2023-04-09"))
                .andExpect(jsonPath("$.reservation.buses[0].id").value(1))
                .andExpect(jsonPath("$.reservation.buses[0].route").value("Saint-Léger - Coulommiers"))
                .andExpect(jsonPath("$.reservation.buses[0].seats").value(5))
                .andExpect(jsonPath("$.reservation.buses[0].departureTime").value("10:00"))
                .andExpect(jsonPath("$.reservation.buses[0].price").value(4.0))
                .andExpect(jsonPath("$.reservation.client.id").value(1))
                .andExpect(jsonPath("$.reservation.client.name").value("Soffiane Boudissa"))
                .andExpect(jsonPath("$.reservation.client.email").value("soffiane.boudissa@gmail.com"))
                .andExpect(jsonPath("$.paymentType").value(PaymentType.PAYPAL.toString()))
                .andExpect(jsonPath("$.paymentInformations").value("soffiane.boudissa@gmail.com"))
                .andExpect(jsonPath("$.totalPrice").value(4.0));

    }
}
