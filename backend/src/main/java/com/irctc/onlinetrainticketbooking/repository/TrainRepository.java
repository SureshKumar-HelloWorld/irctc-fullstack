package com.irctc.onlinetrainticketbooking.repository;

import com.irctc.onlinetrainticketbooking.entity.BookTrain;
import com.irctc.onlinetrainticketbooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TrainRepository extends JpaRepository<BookTrain, Long> {
    List<BookTrain> findByUser(User user);
}
