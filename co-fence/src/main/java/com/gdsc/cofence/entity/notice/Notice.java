package com.gdsc.cofence.entity.notice;

import com.gdsc.cofence.entity.user.RoleType;
import com.gdsc.cofence.entity.user.User;
import com.gdsc.cofence.entity.workplace.WorkPlace;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTICE_ID")
    private Long noticeId;

    @Column(name = "NOTICE_SUBJECT", nullable = false)
    private String noticeSubject;

    @Column(name = "CREATED_TIME")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "NOTICE_DETAIL", length = 1000)
    private String noticeDetail;

    @Column(name = "NOTICE_IMAGE")
    @ElementCollection
    private List<String> noticeImageUrl;

    @Column(name = "TARGET_ROLE", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType targetRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKPLACE_ID")
    private WorkPlace workPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ")
    private User user;
}
