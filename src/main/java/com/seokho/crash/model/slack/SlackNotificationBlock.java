package com.seokho.crash.model.slack;

import java.util.Objects;

public record SlackNotificationBlock(
        String type,
        SlackNotificationText text
){}

