package com.vtcsclubs.backend.dto.responses.events;

import com.vtcsclubs.backend.dto.responses.clubs.ClubResponse;
import com.vtcsclubs.backend.dto.responses.sponsors.SponsorResponse;
import com.vtcsclubs.backend.dto.responses.tags.TagResponse;

import java.time.LocalDateTime;
import java.util.Set;

public record EventDetailsResponse(
        Long eventId,
        String tile,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String description,
        String applicationLink,
        LocalDateTime rsvpDeadline,
        String location,
        ClubResponse club,
        Set<TagResponse> tags,
        Set<SponsorResponse> sponsors) {
}
