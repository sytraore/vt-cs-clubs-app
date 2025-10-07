package com.vtcsclubs.backend.dto.responses.events;

import com.vtcsclubs.backend.dto.responses.sponsors.SponsorResponse;
import com.vtcsclubs.backend.dto.responses.tags.TagResponse;
import com.vtcsclubs.backend.dto.responses.clubs.ClubResponse;

import java.time.LocalDateTime;
import java.util.Set;

public record EventResponse(
        Long eventId,
        String tile,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String location,
        ClubResponse club,
        Set<TagResponse> tags,
        Set<SponsorResponse> sponsors) {
}
