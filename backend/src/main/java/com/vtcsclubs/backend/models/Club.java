package com.vtcsclubs.backend.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "clubs")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String info;

    @Column(unique = true, nullable = false)
    private String contactEmail;

    // A club can have many admins
    // enable deletion of all admins if a club gets deleted
    // enable deletion of admin from database if ever removed from this table
    // map by the club field in club_admins table
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AdminUser> admins = new ArrayList<>();

    // A club can host many events
    // enable deletion of all events if a club gets deleted
    // enable deletion of event from database if ever removed from this table
    // map by the club field in events table
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();

    public Club(){}

    public Club(String name, String info, String contactEmail) {
        this.name = name;
        this.info = info;
        this.contactEmail = contactEmail;
    }

    public Long getClubId() {
        return clubId;
    }

    public void setClubId(Long clubId) {
        this.clubId = clubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public List<AdminUser> getAdmins() {
        return admins;
    }

    public void setAdmins(List<AdminUser> admins) {
        this.admins = admins;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Club club = (Club) o;
        return Objects.equals(getClubId(),
                club.getClubId()) && Objects.equals(getName(),
                club.getName()) && Objects.equals(getInfo(),
                club.getInfo()) && Objects.equals(getContactEmail(),
                club.getContactEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClubId(), getName(), getInfo(), getContactEmail());
    }
}
