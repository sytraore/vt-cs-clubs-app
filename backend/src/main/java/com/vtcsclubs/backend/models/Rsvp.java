package com.vtcsclubs.backend.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "students-rsp")
public class Rsvp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rsvpId;

    @Column(nullable = false)
    private String studentEmail;

    @Column(nullable = false, columnDefinition ="TIMESTAMP")
    private LocalDateTime rsvpTime;

    // a student can register for one event
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    public Rsvp() {
    }

    public Rsvp(String studentEmail, LocalDateTime rspTime) {
        this.studentEmail = studentEmail;
        this.rsvpTime = rspTime;
    }

    public Long getRsvpId() {
        return rsvpId;
    }

    public void setRsvpId(Long rsvpId) {
        this.rsvpId = rsvpId;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public LocalDateTime getRsvpTime() {
        return rsvpTime;
    }

    public void setRsvpTime(LocalDateTime rsvpTime) {
        this.rsvpTime = rsvpTime;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Rsvp rsp = (Rsvp) o;
        return Objects.equals(getRsvpId(), rsp.getRsvpId()) && Objects.equals(getStudentEmail(), rsp.getStudentEmail()) && Objects.equals(getRsvpTime(), rsp.getRsvpTime()) && Objects.equals(getEvent(), rsp.getEvent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRsvpId(), getStudentEmail(), getRsvpTime(), getEvent());
    }
}
