package com.transdev.mobiservices.aop;

import com.transdev.mobiservices.exception.BusNotFoundException;
import com.transdev.mobiservices.exception.ReservationErrorResponse;
import com.transdev.mobiservices.exception.ReservationNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice
public class ReservationExceptionHandler {

    @ResponseBody
    @ExceptionHandler({BusNotFoundException.class,ReservationNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ReservationErrorResponse> handleException(ReservationNotFoundException exc){
        ReservationErrorResponse reservationErrorResponse = new ReservationErrorResponse(HttpStatus.NOT_FOUND,exc.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(reservationErrorResponse, HttpStatus.NOT_FOUND);
    }
}
