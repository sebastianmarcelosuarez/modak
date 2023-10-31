package com.modak.repository;

import com.modak.entity.Notification;
import com.modak.entity.NotificationTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

        Optional<List <Notification>> findByUserIdAndType(String userId, NotificationTypeEnum type);

        @Query("SELECT s FROM Notification s WHERE s.userId = ?1 AND s.type = ?2 AND CAST(s.creationDate AS date) = CURRENT_DATE ORDER BY s.creationDate DESC ")
        Optional<ArrayList<Notification>> findTodayUserTypeMessage(String userId, NotificationTypeEnum type, LocalDateTime creationDate);

    }


