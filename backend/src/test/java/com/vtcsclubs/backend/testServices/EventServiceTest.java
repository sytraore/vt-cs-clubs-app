package com.vtcsclubs.backend.testServices;

import com.vtcsclubs.backend.dto.requests.events.CreateEventRequest;
import com.vtcsclubs.backend.dto.requests.events.UpdateEventRequest;
import com.vtcsclubs.backend.dto.responses.clubs.ClubResponse;
import com.vtcsclubs.backend.dto.responses.events.EventDetailsResponse;
import com.vtcsclubs.backend.dto.responses.events.EventResponse;
import com.vtcsclubs.backend.dto.responses.sponsors.SponsorResponse;
import com.vtcsclubs.backend.dto.responses.tags.TagResponse;
import com.vtcsclubs.backend.exceptions.ResourceNotFoundException;
import com.vtcsclubs.backend.models.Club;
import com.vtcsclubs.backend.models.Event;
import com.vtcsclubs.backend.models.Sponsor;
import com.vtcsclubs.backend.models.Tag;
import com.vtcsclubs.backend.repositories.ClubRepository;
import com.vtcsclubs.backend.repositories.EventRepository;
import com.vtcsclubs.backend.repositories.SponsorRepository;
import com.vtcsclubs.backend.repositories.TagRepository;
import com.vtcsclubs.backend.services.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ClubRepository clubRepository;

    @Mock
    private SponsorRepository sponsorRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private EventService eventService;

    // test data used
    private Event event;
    private Club club;
    private Set<Sponsor> sponsors;
    private Set<Tag> tags;

    /*
    * Initialize the needed entities with some values before each test
    * */
    @BeforeEach
    public void setUp(){
        // dummy club
        club = new Club("VTHacks", "Virginia Tech's largest hackathon.", "contact@vthacks.com");
        club.setClubId(1L);
        // dummy sponsors list
        sponsors = new HashSet<>();
        Sponsor coStar = new Sponsor("CoStar");
        coStar.setSponsorId(1L);
        Sponsor google = new Sponsor("Google");
        google.setSponsorId(2L);
        sponsors.add(coStar);
        sponsors.add(google);

        // dummy tags
        tags = new HashSet<>();
        Tag pythonTag = new Tag("Python", "Technology");
        pythonTag.setTagId(1L);
        Tag aiTag = new Tag("AI/ML", "Field");
        aiTag.setTagId(2L);
        Tag workshopTag = new Tag("Workshop", "EventType");
        workshopTag.setTagId(3L);
        tags.add(pythonTag);
        tags.add(aiTag);
        tags.add(workshopTag);

        LocalDateTime startTime = LocalDateTime.parse("2025-10-25T18:00:00");
        LocalDateTime endTime = LocalDateTime.parse("2025-10-25T20:00:00");
        LocalDateTime rsvpDeadline = LocalDateTime.parse("2025-10-25T20:00:00");
        String description = "Join us for a beginner-friendly workshop on using Python's FastApi framework to build a RESTful API.";
        // dummy event
        event = new Event(startTime, endTime,
                "Learn RESTful APIs development",
                description,
                "",
                "Torgersen Hall 1100",
                rsvpDeadline,
                club);

        event.setEventId(1L);
        event.setTags(tags);
        event.setSponsors(sponsors);
    }

    /*
    * Test if the service method for getting all events in the database works
    * */
    @Test
    public void testGetAllEvents(){
        // tell the event repository to return a list of all the events
        // in this case, it will be only 1 event
        List<Event> events = new ArrayList<>();
        events.add(event);

        // when the mock repository will call the findAll() method, we will return the list of events we created
        when(eventRepository.findAll()).thenReturn(events);

        // when we make the getAllEvents call and the method triggers the findAll() method,
        // it will return our created list of events.
        List<EventResponse> result = eventService.getAllEvents();

        // check if method call was successful
        assertNotNull(result);
        // assert that the size is 1
        assertEquals(1, result.size());
        // assert the event is the right one
        EventResponse resultDto = result.get(0);
        assertEquals(event.getEventId(), resultDto.eventId());
        assertEquals(event.getTitle(), resultDto.title());
        assertEquals(event.getClub().getName(), resultDto.club().name());
        assertEquals(event.getTags().size(), resultDto.tags().size());

        // verify the repository did make a method call
        verify(eventRepository, times(1)).findAll();
    }

    /*
    * Test if the service method for getting one single event in the database works
    * */
    @Test
    public void testGetEventById(){
        // Given this id, check if it is in the database and return the corresponding event
        Long id = 1L;

        // when the mock repository will call the findById(id) method, we will return the event we created
        when(eventRepository.findById(id)).thenReturn(Optional.of(event));

        // should return a detailed event response
        EventDetailsResponse result = eventService.getEventById(id);


        // check result validity
        assertNotNull(result);
        assertEquals(id, result.eventId());
        // at this stage, we're sure it's the correct event
        assertEquals(event.getTitle(), result.title());

        // very method got called once by the repository
        verify(eventRepository, times(1)).findById(id);
    }

    /*
    * Test a case where an event is not in the database
    * */
    @Test
    public void testGetEventByIdException(){
        Long wrongId = 20L;

        when(eventRepository.findById(wrongId)).thenReturn(Optional.empty());

        // should be an error
        assertThrows(ResourceNotFoundException.class, () -> {
            eventService.getEventById(wrongId);
        });

        // very method got called once by the repository
        verify(eventRepository, times(1)).findById(wrongId);
    }

}
