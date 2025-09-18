package com.irctc.onlinetrainticketbooking.service;

import com.irctc.onlinetrainticketbooking.dto.BookTicketRequest;
import com.irctc.onlinetrainticketbooking.dto.BookTicketResponse;
import com.irctc.onlinetrainticketbooking.dto.UpdateTicketRequest;
import java.util.List;

public interface TrainService {
    BookTicketResponse book(BookTicketRequest req, String currentUserEmail);
    BookTicketResponse getByPnr(long pnr, String currentUserEmail);
    List<BookTicketResponse> myTickets(String currentUserEmail);
    BookTicketResponse update(long pnr, UpdateTicketRequest req, String currentUserEmail);
    void cancel(long pnr, String currentUserEmail);

    // Admin operations
    List<BookTicketResponse> allTickets();
    void adminCancel(long pnr);
}
