package com.ngoloc.notification.service;

import com.ngoloc.clients.notification.NotificationRequest;
import com.ngoloc.notification.entity.Notification;
import com.ngoloc.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void send(NotificationRequest notificationRequest) {
        notificationRepository.save(Notification.builder()
                .toCustomerId(notificationRequest.toCustomerId())
                .toCustomerEmail(notificationRequest.toCustomerName())
                .sender("Ngoloc")
                .message(notificationRequest.message())
                .sentAt(LocalDateTime.now())
                .build());
    }
}
