package com.vtcsclubs.backend.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "sponsors")
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sponsorId;

    @Column(nullable = false)
    private String sponsorName;

    // a sponsor can sponsor many events
    @ManyToMany(mappedBy = "sponsors", fetch = FetchType.LAZY)
    private HashSet<Event> events = new HashSet<>();

    public Sponsor() {
    }

    public Sponsor(String sponsor_name) {
        this.sponsorName = sponsor_name;
    }

    public Long getSponsorId() {
        return sponsorId;
    }

    public void setSponsorId(Long sponsorId) {
        this.sponsorId = sponsorId;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }

    public HashSet<Event> getEvents() {
        return events;
    }

    public void setEvents(HashSet<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Sponsor sponsor = (Sponsor) o;
        return Objects.equals(getSponsorId(),
                sponsor.getSponsorId()) && Objects.equals(getSponsorName(),
                sponsor.getSponsorName()) && Objects.equals(getEvents(),
                sponsor.getEvents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSponsorId(), getSponsorName(), getEvents());
    }
}
