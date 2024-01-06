package com.gdsc.cofence.entity.userWorkplace;

import com.gdsc.cofence.entity.user.User;
import com.gdsc.cofence.entity.workplaceManagement.WorkPlace;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "USER_WORKPLACE")
public class UserWorkPlace { // User와 Workplace 사이의 다대다 관계를 위한 일대다, 다대일 관계로 분리해서 관리하기 위한 UserWorkPlace

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "USER_SEQ")
    private User user;

    @ManyToOne
    @JoinColumn(name = "WORKPLACE_ID")
    private WorkPlace workplace;
}
