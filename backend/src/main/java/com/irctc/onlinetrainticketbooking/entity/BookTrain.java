package com.irctc.onlinetrainticketbooking.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class BookTrain {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long pnrNumber;

    private String passengerName;
    private int passengerAge;
    private LocalDate journeyDate;
    private String passengerEmail;
    private String source;
    private String destination;
    private String trainName;
    private int trainNumber;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private int seatNumber;

    @ManyToOne(optional = false)
    private User user;
}
