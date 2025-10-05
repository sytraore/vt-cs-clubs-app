package com.vtcsclubs.backend.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;

@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    // a tag can be assigned to many events
    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private HashSet<Event> events = new HashSet<>();

    public Tag() {
    }

    public Tag(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public Long getTagId() {
        return tagId;
    }

    public void setTagId(Long tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
        Tag tag = (Tag) o;
        return Objects.equals(getTagId(), tag.getTagId()) && Objects.equals(getName(), tag.getName()) && Objects.equals(getCategory(), tag.getCategory()) && Objects.equals(getEvents(), tag.getEvents());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTagId(), getName(), getCategory(), getEvents());
    }
}
