package com.seokho.crash.model.repository;

import com.seokho.crash.model.entity.CrashSessionEntity;
import com.seokho.crash.model.entity.RegistrationEntity;
import com.seokho.crash.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface RegistrationEntityRepository extends JpaRepository<RegistrationEntity, Long> {

    List<RegistrationEntity> findByUser(UserEntity user);

    Optional<RegistrationEntity> findByRegistrationIdAndUser(Long registrationId, UserEntity user);

    Optional<RegistrationEntity> findByUserAndSession(UserEntity user, CrashSessionEntity session);
}
