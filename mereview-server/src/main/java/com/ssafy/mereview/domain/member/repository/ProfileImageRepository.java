package com.ssafy.mereview.domain.member.repository;

import com.ssafy.mereview.domain.member.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
