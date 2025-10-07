package com.vtcsclubs.backend.dto.requests.events;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

public record CreateEventRequest(
        @NotNull
        LocalDateTime startTime,
        @NotNull
        LocalDateTime endTime,
        @NotBlank(message = "Title cannot be blank.")
        @Size(min = 4, max = 40, message = "Title must be between 4 to 40 characters.")
        String title,
        @NotBlank(message = "Description cannot be blank.")
        @Size(min = 25, message = "Description must be at least 25 characters.")
        String description,
        String applicationLink,
        @NotBlank(message = "Location cannot be blank")
        String location,
        @NotNull
        LocalDateTime rsvpDeadline,
        @NotNull
        Long clubId,
        List<Long> tagIds,
        List<Long> sponsorIds) {
}
