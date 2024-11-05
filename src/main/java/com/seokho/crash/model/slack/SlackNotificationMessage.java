package com.seokho.crash.model.slack;

import java.util.List;
import java.util.Objects;

public record SlackNotificationMessage(
        List<SlackNotificationBlock> blocks
) {
}

