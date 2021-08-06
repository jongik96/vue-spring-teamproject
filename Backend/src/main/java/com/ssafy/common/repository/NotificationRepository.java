package com.ssafy.common.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.common.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{

}
