package com.seokho.crash.controller;


import com.seokho.crash.model.entity.UserEntity;
import com.seokho.crash.model.registration.Registration;
import com.seokho.crash.model.registration.RegistrationPostRequestBody;
import com.seokho.crash.service.RegistrationService;
import com.seokho.crash.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/registrations")
public class RegistrationController {



    @Autowired private RegistrationService registrationService;


    @GetMapping
    public ResponseEntity<List<Registration>> getRegistrations(Authentication authentication){
        var registrations =
                registrationService.getRegistrationsByCurrentUser((UserEntity)authentication.getPrincipal());
        return  ResponseEntity.ok(registrations);
    }

    @GetMapping("/{registrationId}")
    public ResponseEntity<Registration> getRegistrationByRegistrationId(
            @PathVariable Long registrationId,
            Authentication authentication
    ){
        var registration = registrationService.getRegistrationByRegistrationIdByCurrentUser(registrationId,(UserEntity)authentication.getPrincipal());
        return ResponseEntity.ok(registration);
    }
    //@Valid는 Java Bean Validation을 통해 객체의 유효성을 검사하는 어노테이션.
    // 주로 @RequestBody와 함께 사용하여 요청 본문에 담긴 데이터가 특정 조건을 충족하는지 자동으로 검증
    //@RequestBody가 JSON을 UserDto 객체로 변환
    @PostMapping
    public ResponseEntity<Registration> createRegistration(
            @Valid @RequestBody RegistrationPostRequestBody registrationPostRequestBody,
            Authentication authentication
            ){
        var registration = registrationService.createRegistrationByCurrentUser(registrationPostRequestBody,(UserEntity)authentication.getPrincipal());
        return  ResponseEntity.ok(registration);
    }



    @DeleteMapping("/{registrationId}")
    public ResponseEntity<Void> deleteRegistration(
            @PathVariable Long registrationId,
            Authentication authentication){
        registrationService.deleteRegistrationByRegistrationIdAndCurrentUser(registrationId,(UserEntity)authentication.getPrincipal());
        return ResponseEntity.noContent().build(); //void로 처리하고. nocontent로 응답
    }


}
