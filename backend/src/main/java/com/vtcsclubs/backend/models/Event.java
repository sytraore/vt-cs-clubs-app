package com.vtcsclubs.backend.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime startTime;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime endTime;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    private String applicationLink;

    @Column(nullable = false)
    private String location;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime respDeadline;

    // an event is hosted by a club and a club can host many events
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    // an event can have many tags
    // an event cannot have duplicated tags, a hash set will handle the duplicates
    // save an event that has a brand-new tag, JPA will also save that new tag to the database
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
          name = "event_tags",
          joinColumns = @JoinColumn(name = "event_id"),
          inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private HashSet<Tag> tags = new HashSet<>();

    // an event can have many sponsors
    // an event cannot have duplicated sponsors, a hash set will handle the duplicates
    // save an event that has a brand-new sponsor, JPA will also save that new sponsor to the database
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "event_sponsors",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "sponsor_id")
    )
    private HashSet<Sponsor> sponsors = new HashSet<>();

    // an event can have many students
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rsvp> registeredStudents = new ArrayList<>();

    public Event() {
    }

    public Event(LocalDateTime startTime,
                 LocalDateTime endTime,
                 String title,
                 String description,
                 String applicationLink,
                 String location,
                 LocalDateTime respDeadline,
                 Club club) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.description = description;
        this.applicationLink = applicationLink;
        this.location = location;
        this.respDeadline = respDeadline;
        this.club = club;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getRespDeadline() {
        return respDeadline;
    }

    public void setRespDeadline(LocalDateTime respDeadline) {
        this.respDeadline = respDeadline;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public HashSet<Tag> getTags() {
        return tags;
    }

    public void setTags(HashSet<Tag> tags) {
        this.tags = tags;
    }

    public HashSet<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(HashSet<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }

    public List<Rsvp> getRegisteredStudents() {
        return registeredStudents;
    }

    public void setRegisteredStudents(List<Rsvp> registeredStudents) {
        this.registeredStudents = registeredStudents;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(getEventId(),
                event.getEventId()) && Objects.equals(getStartTime(),
                event.getStartTime()) && Objects.equals(getEndTime(),
                event.getEndTime()) && Objects.equals(getTitle(),
                event.getTitle()) && Objects.equals(getDescription(),
                event.getDescription()) && Objects.equals(getApplicationLink(),
                event.getApplicationLink()) && Objects.equals(getLocation(),
                event.getLocation()) && Objects.equals(getRespDeadline(),
                event.getRespDeadline()) && Objects.equals(getClub(),
                event.getClub()) && Objects.equals(getTags(),
                event.getTags()) && Objects.equals(getSponsors(),
                event.getSponsors());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEventId(),
                getStartTime(),
                getEndTime(),
                getTitle(),
                getDescription(),
                getApplicationLink(),
                getLocation(),
                getRespDeadline(),
                getClub(),
                getTags(),
                getSponsors());
    }
}
