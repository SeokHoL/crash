package com.seokho.crash.service;

import com.seokho.crash.exception.crashsession.CrashSessionRegistrationStatus;
import com.seokho.crash.exception.registration.RegistrationAlreadyExistsException;
import com.seokho.crash.exception.registration.RegistrationNotFoundException;
import com.seokho.crash.model.entity.RegistrationEntity;
import com.seokho.crash.model.entity.UserEntity;
import com.seokho.crash.model.registration.Registration;
import com.seokho.crash.model.registration.RegistrationPostRequestBody;
import com.seokho.crash.model.repository.RegistrationEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

    @Autowired private RegistrationEntityRepository registrationEntityRepository;

    @Autowired private  CrashSessionService crashSessionService;

    public List<Registration> getRegistrationsByCurrentUser(UserEntity currentUser) {
        var registrationEntities = registrationEntityRepository.findByUser(currentUser);
        return registrationEntities.stream().map(Registration::from).toList();
    }

    public Registration getRegistrationByRegistrationIdByCurrentUser(Long registrationId, UserEntity currentUser) {
        var registrationEntity = getRegistrationEntityByRegistrationIdAndUserEntity(registrationId,currentUser);
        return Registration.from(registrationEntity);
    }

    public RegistrationEntity getRegistrationEntityByRegistrationIdAndUserEntity(
            Long registrationId, UserEntity userEntity){
        // registrationEntityRepository.findByRegistrationIdAndUser 이거의 반환값이 Optional 이기때문에 orElseThrow 로 exception 처리를 하면 됨
        return registrationEntityRepository.findByRegistrationIdAndUser(registrationId,userEntity)
                        .orElseThrow(()-> new RegistrationNotFoundException(registrationId,userEntity));
    }

    public Registration createRegistrationByCurrentUser(
            RegistrationPostRequestBody registrationPostRequestBody, UserEntity currentUser) {
        var crashSessionEntity=
                crashSessionService.getCrashSessionEntityBySessionId(
                registrationPostRequestBody.sessionId());

        registrationEntityRepository
                .findByUserAndSession(currentUser, crashSessionEntity)
                .ifPresent(
                        registrationEntity -> {
                            throw new RegistrationAlreadyExistsException(
                                    registrationEntity.getRegistrationId(),currentUser
                            );
                        }
                );

        var registrationEntity = RegistrationEntity.of(currentUser, crashSessionEntity);
        return Registration.from(
                registrationEntityRepository.save(registrationEntity)
        );

    }

    public void deleteRegistrationByRegistrationIdAndCurrentUser(Long registrationId, UserEntity currentUser) {

        var registrationEntity =getRegistrationEntityByRegistrationIdAndUserEntity(registrationId,currentUser);
        registrationEntityRepository.delete(registrationEntity);
    }

    public CrashSessionRegistrationStatus getCrashSessionRegistrationStatusBySessionIdAndCurrentUser(
            Long sessionId, UserEntity currentUser) {

        var crashSessionEntity = crashSessionService.getCrashSessionEntityBySessionId(sessionId);
        var registrationEntity =
                registrationEntityRepository.findByUserAndSession(currentUser, crashSessionEntity);
        return  new CrashSessionRegistrationStatus(
                sessionId,
                registrationEntity.isPresent(),
                registrationEntity.map(RegistrationEntity::getRegistrationId).orElse(null));
    }
}
