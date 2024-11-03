package com.seokho.crash.controller;


import com.seokho.crash.model.sessionspeaker.SessionSpeaker;
import com.seokho.crash.model.sessionspeaker.SessionSpeakerPatchRequestBody;
import com.seokho.crash.model.sessionspeaker.SessionSpeakerPostRequestBody;
import com.seokho.crash.model.user.User;
import com.seokho.crash.model.user.UserAuthenticationResponse;
import com.seokho.crash.model.user.UserLoginRequestBody;
import com.seokho.crash.model.user.UserSignUpRequestBody;
import com.seokho.crash.service.SessionSpeakerService;
import com.seokho.crash.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/session-speakers")
public class SessionSpeakerController {

    @Autowired
    private SessionSpeakerService sessionSpeakerService;

    @GetMapping
    public ResponseEntity<List<SessionSpeaker>> getSessionSpeakers(){
        var sessionSpeakers = sessionSpeakerService.getSessionSpeakers();
        return  ResponseEntity.ok(sessionSpeakers);
    }

    @GetMapping("/{speakerId}")
    public ResponseEntity<SessionSpeaker> getSessionSpeakerBySpeakerId(
            @PathVariable Long speakerId
    ){
        var sessionSpeaker = sessionSpeakerService.getSessionSpeakerBySpeakerId(speakerId);
        return ResponseEntity.ok(sessionSpeaker);
    }
    //@Valid는 Java Bean Validation을 통해 객체의 유효성을 검사하는 어노테이션.
    // 주로 @RequestBody와 함께 사용하여 요청 본문에 담긴 데이터가 특정 조건을 충족하는지 자동으로 검증
    //@RequestBody가 JSON을 UserDto 객체로 변환
    @PostMapping
    public ResponseEntity<SessionSpeaker> createSessionSpeaker(
            @Valid @RequestBody SessionSpeakerPostRequestBody sessionSpeakerPostRequestBody
    ){
        var sessionSpeaker = sessionSpeakerService.createSessionSpeaker(sessionSpeakerPostRequestBody);
        return  ResponseEntity.ok(sessionSpeaker);
    }

    @PatchMapping("/{speakerId}")
    public ResponseEntity<SessionSpeaker> updateSessionSpeakerBySpeakerId(
            @PathVariable Long speakerId,
            @RequestBody SessionSpeakerPatchRequestBody sessionSpeakerPatchRequestBody){
        var sessionSpeaker = sessionSpeakerService.updateSessionSpeaker(speakerId, sessionSpeakerPatchRequestBody);
        return ResponseEntity.ok(sessionSpeaker);
    }

    @DeleteMapping("/{speakerId}")
    public ResponseEntity<Void> deleteSessionSpeaker(@PathVariable Long speakerId){
        sessionSpeakerService.deleteSessionSpeaker(speakerId);
        return ResponseEntity.noContent().build(); //void로 처리하고. nocontent로 응답
    }


}
