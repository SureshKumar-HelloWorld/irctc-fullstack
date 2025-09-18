package com.irctc.onlinetrainticketbooking.controller;

import com.irctc.onlinetrainticketbooking.dto.BookTicketRequest;
import com.irctc.onlinetrainticketbooking.dto.BookTicketResponse;
import com.irctc.onlinetrainticketbooking.dto.UpdateTicketRequest;
import com.irctc.onlinetrainticketbooking.service.TrainService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/trains")
public class TrainController {

    private final TrainService trainService;
    public TrainController(TrainService trainService) { this.trainService = trainService; }

    @PostMapping("/book")
    public ResponseEntity<BookTicketResponse> book(@Valid @RequestBody BookTicketRequest req, Authentication auth) {
        return ResponseEntity.ok(trainService.book(req, auth.getName()));
    }

    @GetMapping("/me")
    public ResponseEntity<List<BookTicketResponse>> myTickets(Authentication auth) {
        return ResponseEntity.ok(trainService.myTickets(auth.getName()));
    }

    @GetMapping("/{pnr}")
    public ResponseEntity<BookTicketResponse> get(@PathVariable long pnr, Authentication auth) {
        return ResponseEntity.ok(trainService.getByPnr(pnr, auth.getName()));
    }

    @PutMapping("/{pnr}")
    public ResponseEntity<BookTicketResponse> update(@PathVariable long pnr, @Valid @RequestBody UpdateTicketRequest req, Authentication auth) {
        return ResponseEntity.ok(trainService.update(pnr, req, auth.getName()));
    }

    @DeleteMapping("/{pnr}")
    public ResponseEntity<String> cancel(@PathVariable long pnr, Authentication auth) {
        trainService.cancel(pnr, auth.getName());
        return ResponseEntity.ok("Ticket cancelled successfully");
    }
}
