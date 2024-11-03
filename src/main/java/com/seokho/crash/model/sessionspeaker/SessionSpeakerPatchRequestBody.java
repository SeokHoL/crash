package com.seokho.crash.model.sessionspeaker;

import jakarta.validation.constraints.NotEmpty;

public record SessionSpeakerPatchRequestBody(String company, String name, String description) {

    //@NotEmpty 를 쓰지않는 이유는 셋중에 하나만 수정할수도 있어서 붙이지 않음.
}
