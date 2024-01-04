package com.gdsc.cofence.entity.report;

import com.gdsc.cofence.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "REPORT_MANAGEMENT")
public class ReportManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_ID")
    private Long reportId;

    @Column(name = "REPORT_SUBJECT", nullable = false)
    private String reportSubject;

    @Column(name = "CREAT_AT")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "MODIFY_AT")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Column(name = "REPORT_DETAIL", nullable = false)
    private String reportDetail;

    @Column(name = "REPORT_STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    // update와 관련된 메서드 작성 예정

}
