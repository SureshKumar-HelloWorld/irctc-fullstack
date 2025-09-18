package com.irctc.onlinetrainticketbooking.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class UpdateTicketRequest {
    @NotNull private LocalDate journeyDate;
    @NotNull private LocalTime departureTime;
    @NotNull private LocalTime arrivalTime;
    @Positive private int seatNumber;
}
