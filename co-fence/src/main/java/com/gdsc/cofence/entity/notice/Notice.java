package com.gdsc.cofence.entity.notice;

import com.gdsc.cofence.entity.user.RoleType;
import com.gdsc.cofence.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_ID")
    private Long noticeId;

    @Column(name = "NOTICE_SUBJECT", nullable = false)
    private String noticeSubject;

    @Column(name = "TARGET_TYPE") // 공지 대상
    private RoleType noticeType;

    @Column(name = "CREATED_TIME")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "NOTICE_DETAIL")
    private String noticeDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;
}
