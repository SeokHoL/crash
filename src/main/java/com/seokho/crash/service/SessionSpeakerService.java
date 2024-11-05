package com.seokho.crash.service;


import com.seokho.crash.exception.sessionspeaker.SessionSpeakerNotFoundException;
import com.seokho.crash.model.entity.SessionSpeakerEntity;
import com.seokho.crash.repository.SessionSpeakerEntityRepository;
import com.seokho.crash.model.sessionspeaker.SessionSpeaker;
import com.seokho.crash.model.sessionspeaker.SessionSpeakerPatchRequestBody;
import com.seokho.crash.model.sessionspeaker.SessionSpeakerPostRequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class SessionSpeakerService  {

    @Autowired private SessionSpeakerEntityRepository sessionSpeakerEntityRepository;


    public List<SessionSpeaker> getSessionSpeakers() {
       var sessionSpeakerEntities =  sessionSpeakerEntityRepository.findAll();
        return  sessionSpeakerEntities.stream().map(SessionSpeaker::from).toList();
    }
    
    //SessionSpeaker를 조회
    public SessionSpeakerEntity getSessionSpeakerEntityBySpeakerId(Long speakerId) {
        return  sessionSpeakerEntityRepository
                .findById(speakerId)
                .orElseThrow(()-> new SessionSpeakerNotFoundException(speakerId));
    }

    public SessionSpeaker getSessionSpeakerBySpeakerId(Long speakerId) {
        var sessionSpeaker =  getSessionSpeakerEntityBySpeakerId(speakerId);
        return SessionSpeaker.from(sessionSpeaker);
    }

    public SessionSpeaker createSessionSpeaker(
            SessionSpeakerPostRequestBody sessionSpeakerPostRequestBody) {
        var sessionSpeakerEntity = SessionSpeakerEntity.of(
                sessionSpeakerPostRequestBody.company(),
                sessionSpeakerPostRequestBody.name(),
                sessionSpeakerPostRequestBody.description());
        
        //sessionSpeakerEntityRepository 에 entity를 저장시키로 from메소드로 entity를 record로 변환후 controller에 보내기.
        return SessionSpeaker.from(sessionSpeakerEntityRepository.save(sessionSpeakerEntity));
    }

    public SessionSpeaker updateSessionSpeaker(
            Long speakerId, SessionSpeakerPatchRequestBody sessionSpeakerPatchRequestBody) {
        
        var sessionSpeakerEntity = getSessionSpeakerEntityBySpeakerId(speakerId);

        if (!ObjectUtils.isEmpty(sessionSpeakerPatchRequestBody.company())){
            sessionSpeakerEntity.setCompany(sessionSpeakerPatchRequestBody.company());
        }
        if (!ObjectUtils.isEmpty(sessionSpeakerPatchRequestBody.name())){
            sessionSpeakerEntity.setName(sessionSpeakerPatchRequestBody.name());
        }
        if (!ObjectUtils.isEmpty(sessionSpeakerPatchRequestBody.description())){
            sessionSpeakerEntity.setDescription(sessionSpeakerPatchRequestBody.description());
        }

        return SessionSpeaker.from(
                sessionSpeakerEntityRepository.save(sessionSpeakerEntity));
    }

    public void deleteSessionSpeaker(Long speakerId) {
        var sessionSpeakerEntity = getSessionSpeakerEntityBySpeakerId(speakerId);
        sessionSpeakerEntityRepository.delete(sessionSpeakerEntity);
    }
}
