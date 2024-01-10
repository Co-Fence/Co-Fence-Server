package com.gdsc.cofence.entity.workplaceManagement;

import com.gdsc.cofence.entity.user.User;
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
    private List<User> users = new ArrayList<>();

    @Builder
    public WorkPlace(Long id, String name, String address) {
        this.workplaceId = id;
        this.workplaceName = name;
        this.workplaceAddress = address;
    }
    // 수정사항에 사용할 간단한 메서드 구현 예정
}
