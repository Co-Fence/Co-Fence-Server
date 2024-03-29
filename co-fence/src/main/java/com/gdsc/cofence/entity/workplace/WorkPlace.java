package com.gdsc.cofence.entity.workplace;

import com.gdsc.cofence.entity.attendence.Attendance;
import com.gdsc.cofence.entity.notice.Notice;
import com.gdsc.cofence.entity.report.ReportManagement;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORKPLACE_ID")
    private Long workplaceId;

    @Column(name = "WORKPLACE_NAME", nullable = false)
    private String workplaceName;

    @Column(name = "WORKPLACE_ADDRESS")
    private String workplaceAddress;

    @OneToMany(mappedBy = "workPlace", fetch = FetchType.LAZY)
    private List<Attendance> attendances = new ArrayList<>();

    @OneToMany(mappedBy = "reportedWorkplace", fetch = FetchType.LAZY)
    private List<ReportManagement> reportManagements = new ArrayList<>();

    @OneToMany(mappedBy = "workPlace", fetch = FetchType.LAZY)
    private List<Notice> notices = new ArrayList<>();

    @Builder
    public WorkPlace(Long id, String name, String address) {
        this.workplaceId = id;
        this.workplaceName = name;
        this.workplaceAddress = address;
    }
    // 수정사항에 사용할 간단한 메서드 구현 예정
}
