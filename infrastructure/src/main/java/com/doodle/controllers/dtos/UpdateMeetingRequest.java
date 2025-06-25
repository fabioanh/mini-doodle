package com.doodle.controllers.dtos;

import java.util.List;

public record UpdateMeetingRequest(
        String title,
        String description,
        List<String> participants
) {
}
