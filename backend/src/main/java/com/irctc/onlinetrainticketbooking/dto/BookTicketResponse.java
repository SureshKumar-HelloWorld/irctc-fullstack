package com.irctc.onlinetrainticketbooking.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookTicketResponse {
    private long pnrNumber;
    private String passengerName;
    private LocalDate journeyDate;
    private String source;
    private String destination;
    private String trainName;
    private int trainNumber;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int seatNumber;
    private String bookingStatus;
}
