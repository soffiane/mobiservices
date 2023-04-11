package com.transdev.mobiservices.aop;

import com.transdev.mobiservices.exception.InsufficentSeatsException;
import com.transdev.mobiservices.exception.ReservationErrorResponse;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
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
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ReservationErrorResponse> handleException(ResourceNotFoundException exc){
        ReservationErrorResponse reservationErrorResponse = new ReservationErrorResponse(HttpStatus.NOT_FOUND,exc.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(reservationErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(InsufficentSeatsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ReservationErrorResponse> handleException(InsufficentSeatsException exc){
        ReservationErrorResponse reservationErrorResponse = new ReservationErrorResponse(HttpStatus.BAD_REQUEST,exc.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(reservationErrorResponse, HttpStatus.BAD_REQUEST);
    }
}
