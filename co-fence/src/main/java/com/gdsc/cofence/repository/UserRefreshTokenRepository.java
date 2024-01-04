package com.gdsc.cofence.repository;

import com.gdsc.cofence.entity.user.User;
import com.gdsc.cofence.entity.user.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {

    Optional<UserRefreshToken> findByUser_UserSeq(Long userSeq);

    void deleteByUser(User user);
}
