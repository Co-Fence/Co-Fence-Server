package com.gdsc.cofence.entity.report;

import com.gdsc.cofence.entity.user.User;
import com.gdsc.cofence.entity.workplace.WorkPlace;
import jakarta.persistence.Column;
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
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class ReportManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REPORT_ID")
    private Long reportId;

    @Column(name = "REPORT_SUBJECT", nullable = false)
    private String reportSubject;

    @Column(name = "REPORT_DETAIL", nullable = false)
    private String reportDetail;

    @Column(name = "REPORT_URL")
    private String reportImageUrl;

    @Column(name = "CREAT_AT")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "MODIFY_AT")
    @LastModifiedDate
    private LocalDateTime modifiedAt;

    @Column(name = "ACTION_STATUS")
    @Enumerated(EnumType.STRING)
    private ActionStatus actionStatus;

    @Column(name = "REPORT_STATUS")
    @Enumerated(EnumType.STRING)
    private ReportStatus reportStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "reportManagements")
    private WorkPlace reportedWorkplace;

    // update와 관련된 메서드 작성 예정

    public void updateReportOnlyAdmin(String reportSubject, ActionStatus actionStatus, ReportStatus reportStatus, LocalDateTime modifiedAt) {
        this.reportSubject = reportSubject;
        this.actionStatus = actionStatus;
        this.reportStatus = reportStatus;
        this.modifiedAt = modifiedAt;
    }
}
