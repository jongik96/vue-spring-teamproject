package com.ssafy.common.domain;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

// import com.ssafy.common.domain.Member;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Notification {
  @Id
  @GeneratedValue
  @Column(name="NOTIFICATION_NO")
  private long notificationNo;

  @ManyToOne
  @JoinColumn(name = "NOTIFICATION_SENDER")
  private Member notificationSender;

  @ManyToOne
  @JoinColumn(name = "NOTIFICATION_RECIEVER")
  private Member notificationReciever;

  @Column(name="NOTIFICATION_READ_STATUS")
  private boolean notificationReadStatus;

  @Column(length =3,name= "NOTIFICATION_CLASS")
  @Enumerated(EnumType.STRING)
  private Notification_Class notificationClass;

  @Column(name = "NOTIFICATION_DATE")
  private Timestamp notificationDate;
}
