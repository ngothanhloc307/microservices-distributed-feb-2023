package com.ngoloc.notification.rabbitmq;

import com.ngoloc.clients.notification.NotificationRequest;
import com.ngoloc.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queues.notification}")
    public void consumer(NotificationRequest notificationRequest) {
        log.info("Consumer {} from queue", notificationRequest);
        notificationService.send(notificationRequest);
    }
}
