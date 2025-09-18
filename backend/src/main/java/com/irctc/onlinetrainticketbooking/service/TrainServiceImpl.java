package com.irctc.onlinetrainticketbooking.service;

import com.irctc.onlinetrainticketbooking.dto.BookTicketRequest;
import com.irctc.onlinetrainticketbooking.dto.BookTicketResponse;
import com.irctc.onlinetrainticketbooking.dto.UpdateTicketRequest;
import com.irctc.onlinetrainticketbooking.entity.BookTrain;
import com.irctc.onlinetrainticketbooking.entity.User;
import com.irctc.onlinetrainticketbooking.exception.NotFoundException;
import com.irctc.onlinetrainticketbooking.repository.TrainRepository;
import com.irctc.onlinetrainticketbooking.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainServiceImpl implements TrainService {

    private final TrainRepository trainRepo;
    private final UserRepository userRepo;

    public TrainServiceImpl(TrainRepository trainRepo, UserRepository userRepo) {
        this.trainRepo = trainRepo;
        this.userRepo = userRepo;
    }

    private BookTicketResponse toResponse(BookTrain t) {
        BookTicketResponse r = new BookTicketResponse();
        r.setPnrNumber(t.getPnrNumber());
        r.setPassengerName(t.getPassengerName());
        r.setJourneyDate(t.getJourneyDate());
        r.setSource(t.getSource());
        r.setDestination(t.getDestination());
        r.setTrainName(t.getTrainName());
        r.setTrainNumber(t.getTrainNumber());
        r.setDepartureTime(t.getDepartureTime());
        r.setArrivalTime(t.getArrivalTime());
        r.setSeatNumber(t.getSeatNumber());
        r.setBookingStatus("CONFIRMED");
        return r;
    }

    private User findUser(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }

    private BookTrain mustOwn(long pnr, String email) {
        User u = findUser(email);
        BookTrain t = trainRepo.findById(pnr).orElseThrow(() -> new NotFoundException("Ticket not found"));
        if (!t.getUser().getId().equals(u.getId())) {
            throw new NotFoundException("Ticket not found");
        }
        return t;
    }

    @Override
    public BookTicketResponse book(BookTicketRequest req, String currentUserEmail) {
        User u = findUser(currentUserEmail);
        BookTrain t = new BookTrain();
        t.setPassengerName(req.getPassengerName());
        t.setPassengerAge(req.getPassengerAge());
        t.setJourneyDate(req.getJourneyDate());
        t.setPassengerEmail(req.getPassengerEmail());
        t.setSource(req.getSource());
        t.setDestination(req.getDestination());
        t.setTrainName(req.getTrainName());
        t.setTrainNumber(req.getTrainNumber());
        t.setDepartureTime(req.getDepartureTime());
        t.setArrivalTime(req.getArrivalTime());
        t.setSeatNumber(req.getSeatNumber());
        t.setUser(u);
        return toResponse(trainRepo.save(t));
    }

    @Override
    public BookTicketResponse getByPnr(long pnr, String currentUserEmail) {
        return toResponse(mustOwn(pnr, currentUserEmail));
    }

    @Override
    public List<BookTicketResponse> myTickets(String currentUserEmail) {
        User u = findUser(currentUserEmail);
        return trainRepo.findByUser(u).stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public BookTicketResponse update(long pnr, UpdateTicketRequest req, String currentUserEmail) {
        BookTrain t = mustOwn(pnr, currentUserEmail);
        t.setJourneyDate(req.getJourneyDate());
        t.setDepartureTime(req.getDepartureTime());
        t.setArrivalTime(req.getArrivalTime());
        t.setSeatNumber(req.getSeatNumber());
        return toResponse(trainRepo.save(t));
    }

    @Override
    public void cancel(long pnr, String currentUserEmail) {
        BookTrain t = mustOwn(pnr, currentUserEmail);
        trainRepo.delete(t);
    }

    @Override
    public List<BookTicketResponse> allTickets() {
        return trainRepo.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public void adminCancel(long pnr) {
        BookTrain t = trainRepo.findById(pnr).orElseThrow(() -> new NotFoundException("Ticket not found"));
        trainRepo.delete(t);
    }
}
