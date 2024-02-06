package com.gdsc.cofence.repository;

import com.gdsc.cofence.entity.notice.Notice;
import com.gdsc.cofence.entity.user.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>,
        JpaSpecificationExecutor<Notice> {

    Page<Notice> findByWorkPlace_WorkplaceIdAndNoticeSubjectContainingAndTargetRole(
            Long workplaceId,
            String noticeSubject,
            RoleType targetRoletype,
            Pageable pageable
    );
}
