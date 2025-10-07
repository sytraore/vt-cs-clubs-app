package com.vtcsclubs.backend.services;

import com.vtcsclubs.backend.dto.requests.events.UpdateEventRequest;
import com.vtcsclubs.backend.dto.responses.clubs.ClubResponse;
import com.vtcsclubs.backend.dto.requests.events.CreateEventRequest;
import com.vtcsclubs.backend.dto.responses.events.EventDetailsResponse;
import com.vtcsclubs.backend.dto.responses.events.EventResponse;
import com.vtcsclubs.backend.dto.responses.sponsors.SponsorResponse;
import com.vtcsclubs.backend.dto.responses.tags.TagResponse;
import com.vtcsclubs.backend.exceptions.ResourceNotFoundException;
import com.vtcsclubs.backend.models.Club;
import com.vtcsclubs.backend.models.Event;
import com.vtcsclubs.backend.models.Sponsor;
import com.vtcsclubs.backend.models.Tag;
import com.vtcsclubs.backend.repositories.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/*
* Business logic for Events Management
* */
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final ClubRepository clubRepository;
    private final RsvpRepository rsvpRepository;
    private final SponsorRepository sponsorRepository;
    private final TagRepository tagRepository;

    public EventService(EventRepository eventRepository,
                        ClubRepository clubRepository,
                        RsvpRepository rsvpRepository,
                        SponsorRepository sponsorRepository,
                        TagRepository tagRepository) {
        this.eventRepository = eventRepository;
        this.clubRepository = clubRepository;
        this.rsvpRepository = rsvpRepository;
        this.sponsorRepository = sponsorRepository;
        this.tagRepository = tagRepository;
    }

    /*
     * Convert a club to a sponsor dto for endpoint response
     * Takes a Club object and returns a Club object
     * */
    private ClubResponse fromClubToClubDto(Club club){
        return new ClubResponse(
                club.getClubId(),
                club.getName());
    }

    /*
     * Convert a tag to a tag dto for endpoint response
     * Takes a Tag object and returns a TagResponse object
     * */
    private TagResponse fromTagToTagDto(Tag tag){
        return new TagResponse(
                tag.getTagId(),
                tag.getName(),
                tag.getCategory());
    }

    /*
    * Convert a sponsor to a sponsor dto for endpoint response
    * Takes a Sponsor object and returns a SponsorResponse object
    * */
    private SponsorResponse fromSponsorToSponsorDto(Sponsor sponsor){
        return new SponsorResponse(
                sponsor.getSponsorId(),
                sponsor.getSponsorName());
    }


    /*
    * Convert an event to an event dto for endpoint response
    * Takes an Event object and return an EventResponse object
    * */
    private EventResponse fromEventToEventDto(Event theEvent){
        // get the club dto
        ClubResponse clubDto = fromClubToClubDto(theEvent.getClub());

        // get the tags
        Set<Tag> tags = theEvent.getTags();
        // convert each tag to a tag dto
        Set<TagResponse> tagDtos = tags.stream().map(this::fromTagToTagDto).collect(Collectors.toSet());

        // get the sponsors
        Set<Sponsor> sponsors = theEvent.getSponsors();
        // convert each tag to a tag dto
        Set<SponsorResponse> sponsorDtos = sponsors.stream().map(this::fromSponsorToSponsorDto).collect(Collectors.toSet());

        // get the event dto
        return new EventResponse(
                theEvent.getEventId(),
                theEvent.getTitle(),
                theEvent.getStartTime(),
                theEvent.getEndTime(),
                theEvent.getLocation(),
                clubDto,
                tagDtos,
                sponsorDtos
        );
    }

    /*
     * Convert an event to an event dto for endpoint response
     * Takes an Event object and return an EventResponse object
     * */
    private EventDetailsResponse fromEventToEventDetailsDto(Event event){
        // get the club
        ClubResponse clubDto = fromClubToClubDto(event.getClub());

        // get the tags
        Set<Tag> tags = event.getTags();
        // convert each tag to a tag dto
        Set<TagResponse> tagDtos = tags.stream().map(this::fromTagToTagDto).collect(Collectors.toSet());

        // get the sponsors
        Set<Sponsor> sponsors = event.getSponsors();
        // convert each tag to a tag dto
        Set<SponsorResponse> sponsorDtos = sponsors.stream().map(this::fromSponsorToSponsorDto).collect(Collectors.toSet());
        // convert to dto response
        return new EventDetailsResponse(
                event.getEventId(),
                event.getTitle(),
                event.getStartTime(),
                event.getEndTime(),
                event.getDescription(),
                event.getApplicationLink(),
                event.getRsvpDeadline(),
                event.getLocation(),
                clubDto,
                tagDtos,
                sponsorDtos
        );
    }

    /*
    * Returns a list of all the current events in the database
    * */
    @Transactional(readOnly = true)
    public List<EventResponse> getAllEvents(){

        List<Event> events = eventRepository.findAll();
        // convert each event in the list to an event response dto to deal with ConcurrentModification errors
        return events.stream().map(this::fromEventToEventDto).collect(Collectors.toList());
    }

    /*
    *   Look for a specific event based on an id in the event table.
    *   If found, return the event. Else, return error not found.
    * */
    @Transactional(readOnly = true)
    public EventDetailsResponse getEventById(Long id){
        // event found
        Event event = eventRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("This event was not found in database."));

        return fromEventToEventDetailsDto(event);
    }

    /*
    * TO DO:
    * Get events by title
    * Get events by tag
    * Get events by sponsor name
    * Get events by club name
    * Get events by date
    * */

    /*
    * Create a brand-new event for a specific club.
    * Club must exist in database
    * Return new event successfully created
    * */
    public EventDetailsResponse createEvent(CreateEventRequest request){
        // create the necessary objects
        Event newEvent = new Event();
        Club theClub = clubRepository.findById(request.clubId()).orElseThrow(
                () -> new ResourceNotFoundException("No club found."));
        List<Tag> tags = tagRepository.findAllById(request.tagIds());
        List<Sponsor> sponsors = sponsorRepository.findAllById(request.sponsorIds());

        // remove duplicates if any
        Set<Tag> uniqueTags = new HashSet<>(tags);
        Set<Sponsor> uniqueSponsors = new HashSet<>(sponsors);

        // set the new event properties
        newEvent.setStartTime(request.startTime());
        newEvent.setEndTime(request.endTime());
        newEvent.setTitle(request.title());
        newEvent.setDescription(request.description());
        newEvent.setApplicationLink(request.applicationLink());
        newEvent.setLocation(request.location());
        newEvent.setRsvpDeadline(request.rsvpDeadline());
        newEvent.setClub(theClub);
        newEvent.setTags(uniqueTags);
        newEvent.setSponsors(uniqueSponsors);

        // save to db
        // response body
        return fromEventToEventDetailsDto(eventRepository.save(newEvent));
    }

    /*
    * Update an event. Return edited event
    * */
    public EventDetailsResponse updateEvent(Long eventId, UpdateEventRequest request){
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new ResourceNotFoundException("Event not found."));

        // update what needs to be updated
        if (request.getTitle() != null){
            event.setTitle(request.getTitle());
        }
        if (request.getDescription() != null){
            event.setDescription(request.getDescription());
        }
        if (request.getStartTime() != null){
            event.setStartTime(request.getStartTime());
        }
        if (request.getEndTime() != null){
            event.setEndTime(request.getEndTime());
        }
        if (request.getApplicationLink() != null){
            event.setApplicationLink(request.getApplicationLink());
        }
        if (request.getLocation() != null){
            event.setLocation(request.getLocation());
        }
        if (request.getTagIds() != null){
            List<Tag> tags = tagRepository.findAllById(request.getTagIds());
            Set<Tag> tagSet = new HashSet<>(tags);
            event.setTags(tagSet);
        }
        if (request.getSponsorIds() != null){
            List<Sponsor> sponsors = sponsorRepository.findAllById(request.getSponsorIds());
            Set<Sponsor> sponsorSet = new HashSet<>(sponsors);
            event.setSponsors(sponsorSet);
        }
        if (request.getRsvpDeadline() != null){
            event.setRsvpDeadline(request.getRsvpDeadline());
        }

        // save and return dto as body response
        return fromEventToEventDetailsDto(eventRepository.save(event));
    }

    /*
    * Delete an event by id
    * */
    public void deleteEventById(Long eventId){
        // check dor event existence in db
        if (!eventRepository.existsById(eventId)){
            throw new ResourceNotFoundException("This event was not found in database");
        }
        eventRepository.deleteById(eventId);
    }

}
