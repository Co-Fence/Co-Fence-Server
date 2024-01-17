package com.gdsc.cofence.repository;

import com.gdsc.cofence.entity.attendence.Attendance;
import com.gdsc.cofence.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByWorkPlace_WorkplaceId(Long workplaceId);

    Optional<Attendance> findByUserAndWorkPlace_WorkplaceId(User user, Long workplaceId);

    Attendance findFirstByUser_UserSeqOrderByAttendTimeDesc(Long userSeq);

}
