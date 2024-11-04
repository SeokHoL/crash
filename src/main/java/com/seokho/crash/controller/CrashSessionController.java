package com.seokho.crash.controller;


import com.seokho.crash.exception.crashsession.CrashSessionRegistrationStatus;
import com.seokho.crash.model.crashsession.CrashSession;
import com.seokho.crash.model.crashsession.CrashSessionPatchRequestBody;
import com.seokho.crash.model.crashsession.CrashSessionPostRequestBody;
import com.seokho.crash.model.entity.UserEntity;
import com.seokho.crash.model.sessionspeaker.SessionSpeaker;
import com.seokho.crash.model.sessionspeaker.SessionSpeakerPatchRequestBody;
import com.seokho.crash.model.sessionspeaker.SessionSpeakerPostRequestBody;
import com.seokho.crash.service.CrashSessionService;
import com.seokho.crash.service.RegistrationService;
import com.seokho.crash.service.SessionSpeakerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crash-sessions")
public class CrashSessionController {

    @Autowired private CrashSessionService crashSessionService;

    @Autowired private RegistrationService registrationService;



    @GetMapping
    public ResponseEntity<List<CrashSession>> getCrashSessions(){
        var crashSessions = crashSessionService.getCrashSessions();
        return  ResponseEntity.ok(crashSessions);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<CrashSession> getCrashSessionBySessionId(
            @PathVariable Long sessionId){
        var crashSession = crashSessionService.getCrashSessionBySessionId(sessionId);
        return ResponseEntity.ok(crashSession);
    }

    @GetMapping("/{sessionId}/registration-status")
    public ResponseEntity<CrashSessionRegistrationStatus> getCrashSessionRegistrationStatusBySessionId(
            @PathVariable Long sessionId,
            Authentication authentication){
        var registrationStatus =
                registrationService.getCrashSessionRegistrationStatusBySessionIdAndCurrentUser(
                        sessionId, (UserEntity) authentication.getPrincipal());

        return ResponseEntity.ok(registrationStatus);
    }
    //@Valid는 Java Bean Validation을 통해 객체의 유효성을 검사하는 어노테이션.
    // 주로 @RequestBody와 함께 사용하여 요청 본문에 담긴 데이터가 특정 조건을 충족하는지 자동으로 검증
    //@RequestBody가 JSON을 UserDto 객체로 변환
    @PostMapping
    public ResponseEntity<CrashSession> createCrashSession(
            @Valid @RequestBody CrashSessionPostRequestBody crashSessionPostRequestBody
            ){
        var crashSession = crashSessionService.createCrashSession(crashSessionPostRequestBody);
        return  ResponseEntity.ok(crashSession);
    }

    @PatchMapping("/{sessionId}")
    public ResponseEntity<CrashSession> updateCrashSession(
            @PathVariable Long sessionId,
            @RequestBody CrashSessionPatchRequestBody crashSessionPatchRequestBody){
        var crashSession = crashSessionService.updateCrashSession(sessionId, crashSessionPatchRequestBody);
        return ResponseEntity.ok(crashSession);
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteCrashSession(@PathVariable Long sessionId){
        crashSessionService.deleteCrashSession(sessionId);
        return ResponseEntity.noContent().build(); //void로 처리하고. nocontent로 응답
    }


}
