package com.gdsc.cofence.entity.attendence;

import com.gdsc.cofence.entity.user.User;
import com.gdsc.cofence.entity.workplace.WorkPlace;
import jakarta.persistence.Access;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ATTENDENCE_ID")
    private Long attendanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_SEQ")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORKPLACE_ID")
    private WorkPlace workPlace;

    @Column(name = "ATTEND_TIME")
    private LocalDateTime attendTime;

    @Column(name = "LEAVE_TIME")
    private LocalDateTime leaveTime;

    @Builder
    public Attendance(User user, WorkPlace workPlace, LocalDateTime attendTime, LocalDateTime leaveTime) {
        this.user = user;
        this.workPlace = workPlace;
        this.attendTime = attendTime;
        this.leaveTime = leaveTime;
    }
}
