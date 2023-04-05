package com.transdev.mobiservices.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ReservationErrorResponse {
    private HttpStatus status;
    private String message;
    private LocalDateTime timestamp;
}
