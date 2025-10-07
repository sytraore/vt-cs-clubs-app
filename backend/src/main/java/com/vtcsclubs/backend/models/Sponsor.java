package com.vtcsclubs.backend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "sponsors")
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sponsorId;

    @Column(nullable = false)
    private String sponsorName;

    // a sponsor can sponsor many events
    @JsonBackReference
    @ManyToMany(mappedBy = "sponsors", fetch = FetchType.LAZY)
    private Set<Event> events = new HashSet<>();

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

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Sponsor sponsor = (Sponsor) o;
        return Objects.equals(sponsorId, sponsor.sponsorId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(sponsorId);
    }
}
