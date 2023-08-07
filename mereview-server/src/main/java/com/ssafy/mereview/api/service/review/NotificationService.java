package com.ssafy.mereview.api.service.review;

import com.ssafy.mereview.api.service.review.dto.response.NotificationResponse;
import com.ssafy.mereview.domain.review.entity.Notification;
import com.ssafy.mereview.domain.review.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationResponse toggleStatus(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 알림입니다."));

        notification.toggleStatus();

        return NotificationResponse.of(notification);
    }

    public Long delete(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 알림입니다."));
        notificationRepository.delete(notification);
        return notificationId;
    }
}
