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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    // 해당 작업장에 이런 신고내용이 들어왔었음을 기록하는 용도의 Attribute임
    // 신고한 사용자가 작업장을 바꿔도 이 작업장에서 이런 신고를 이 사용자가 했음의 데이터는 변화가 있어서는 안된다고 생각하기에 이렇게 했음
    @Column(name = "REPORTED_WORKPLACE_ID")
    private Long reportedWorkplaceId;

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



    // update와 관련된 메서드 작성 예정

}
