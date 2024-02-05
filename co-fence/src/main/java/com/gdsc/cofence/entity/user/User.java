package com.gdsc.cofence.entity.user;

import com.gdsc.cofence.dto.userDto.userResponse.UserListResponseDto;
import com.gdsc.cofence.entity.attendence.Attendance;
import com.gdsc.cofence.entity.notice.Notice;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User {

    @JsonIgnore
    @Id
    @Column(name = "USER_SEQ", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(name = "USER_NAME", length = 100, nullable = false)
    private String userName;

    @Column(name = "USER_NATIONALITY")
    private String nationality;

    @JsonIgnore
    @Column(name = "USER_PHONE_NUMBER", length = 128, nullable = false)
    private String phoneNumber;

    @Column(name = "USER_EMAIL", length = 512, unique = true, nullable = false)
    private String email;

    @Column(name = "EMAIL_IMAGE_URL", length = 512)
    private String profileImageUrl;

    @Column(name = "ROLE_TYPE", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @CreatedDate
    @Column(name = "CREATED_AT", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Attendance> attendances;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Notice> notices = new ArrayList<>();

    public UserListResponseDto toDto() {
      return UserListResponseDto.builder()
              .userId(this.userSeq)
              .userName(this.userName)
              .profileImageUrl(this.profileImageUrl)
              .roleType(this.roleType.toString())
              .build();
    }

    // 사용자 정보 수정사항에 있어서 update메서드등 새로 만들어야함
}
