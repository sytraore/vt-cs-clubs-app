package com.vtcsclubs.backend.dto.requests.events;

import com.vtcsclubs.backend.models.Sponsor;
import com.vtcsclubs.backend.models.Tag;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateEventRequest {

    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    private String applicationLink;
    private LocalDateTime rsvpDeadline;
    private String location;
    private List<Long> tagIds;
    private List<Long> sponsorIds;

    public UpdateEventRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String tile) {
        this.title = tile;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApplicationLink() {
        return applicationLink;
    }

    public void setApplicationLink(String applicationLink) {
        this.applicationLink = applicationLink;
    }

    public LocalDateTime getRsvpDeadline() {
        return rsvpDeadline;
    }

    public void setRsvpDeadline(LocalDateTime rsvpDeadline) {
        this.rsvpDeadline = rsvpDeadline;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public List<Long> getSponsorIds() {
        return sponsorIds;
    }

    public void setSponsorIds(List<Long> sponsorIds) {
        this.sponsorIds = sponsorIds;
    }
}
