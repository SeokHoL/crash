package com.seokho.crash.exception.crashsession;


import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CrashSessionRegistrationStatus(
        Long sessionId,
        boolean isRegistered,
        Long registrationId) {

}
