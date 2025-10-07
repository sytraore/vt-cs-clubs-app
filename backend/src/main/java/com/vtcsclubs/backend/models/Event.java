package com.vtcsclubs.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private LocalDateTime rsvpDeadline;

    // an event is hosted by a club and a club can host many events
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    // an event can have many tags
    // an event cannot have duplicated tags, a hash set will handle the duplicates
    // save an event that has a brand-new tag, JPA will also save that new tag to the database
    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
          name = "event_tags",
          joinColumns = @JoinColumn(name = "event_id"),
          inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    // an event can have many sponsors
    // an event cannot have duplicated sponsors, a hash set will handle the duplicates
    // save an event that has a brand-new sponsor, JPA will also save that new sponsor to the database
    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "event_sponsors",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "sponsor_id")
    )
    private Set<Sponsor> sponsors = new HashSet<>();

    // an event can have many students
    @JsonManagedReference
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
                 LocalDateTime rsvpDeadline,
                 Club club) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.title = title;
        this.description = description;
        this.applicationLink = applicationLink;
        this.location = location;
        this.rsvpDeadline = rsvpDeadline;
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

    public LocalDateTime getRsvpDeadline() {
        return rsvpDeadline;
    }

    public void setRsvpDeadline(LocalDateTime rsvpDeadline) {
        this.rsvpDeadline = rsvpDeadline;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(Set<Sponsor> sponsors) {
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        // The equality of an entity should only depend on its ID.
        return eventId != null && Objects.equals(eventId, event.eventId);
    }

    @Override
    public int hashCode() {
        // The hash code should also only depend on the ID.
        return Objects.hash(eventId);
    }
}
