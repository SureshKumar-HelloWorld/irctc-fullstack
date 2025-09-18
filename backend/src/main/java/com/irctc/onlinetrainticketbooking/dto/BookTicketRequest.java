package com.irctc.onlinetrainticketbooking.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookTicketRequest {
    @NotBlank private String passengerName;
    @Min(0) @Max(120) private int passengerAge;
    @NotNull private LocalDate journeyDate;
    @Email @NotBlank private String passengerEmail;
    @NotBlank private String source;
    @NotBlank private String destination;
    @NotBlank private String trainName;
    private int trainNumber;
    @NotNull private LocalTime departureTime;
    @NotNull private LocalTime arrivalTime;
    @Positive private int seatNumber;
}
