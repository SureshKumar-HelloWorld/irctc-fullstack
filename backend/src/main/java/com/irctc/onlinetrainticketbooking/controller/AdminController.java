package com.irctc.onlinetrainticketbooking.controller;

import com.irctc.onlinetrainticketbooking.dto.BookTicketResponse;
import com.irctc.onlinetrainticketbooking.service.TrainService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final TrainService trainService;
    public AdminController(TrainService trainService) { this.trainService = trainService; }

    @GetMapping("/tickets")
    public ResponseEntity<List<BookTicketResponse>> allTickets() {
        return ResponseEntity.ok(trainService.allTickets());
    }

    @DeleteMapping("/tickets/{pnr}")
    public ResponseEntity<String> cancel(@PathVariable long pnr) {
        trainService.adminCancel(pnr);
        return ResponseEntity.ok("Ticket cancelled by admin");
    }
}
