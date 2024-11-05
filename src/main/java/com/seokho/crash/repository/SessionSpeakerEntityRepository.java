package com.seokho.crash.repository;

import com.seokho.crash.model.entity.SessionSpeakerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionSpeakerEntityRepository extends JpaRepository<SessionSpeakerEntity, Long> {


}
