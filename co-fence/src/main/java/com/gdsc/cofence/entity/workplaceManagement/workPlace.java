package com.gdsc.cofence.entity.workplaceManagement;

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
@Table(name = "WORKPLACE")
public class workPlace {

    // 데모 형태로 만들면(우리가 직접 작업 현장에 대해서 DB에 추가를 해두는거)
    // 사용자가 현장 작업을 마치면 현장 정보가 사용자 뷰에서 없어져야함(이걸 고려해야함)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WORKPLACE_ID")
    private Long workplaceId;

    @Column(name = "WORKPLACE_NAME", nullable = false)
    private String workplaceName;

    @Column(name = "WORKPLACE_ADDRESS")
    private String workplaceAddress;

    @Column(name = "ADDRESS_DETAIL")
    private String workplaceAddressDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    // 수정사항에 사용할 간단한 메서드 구현 예정
}
