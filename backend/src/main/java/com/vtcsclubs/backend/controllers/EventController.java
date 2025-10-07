package com.vtcsclubs.backend.controllers;

import com.vtcsclubs.backend.dto.requests.events.CreateEventRequest;
import com.vtcsclubs.backend.dto.requests.events.UpdateEventRequest;
import com.vtcsclubs.backend.dto.responses.events.EventDetailsResponse;
import com.vtcsclubs.backend.dto.responses.events.EventResponse;
import com.vtcsclubs.backend.models.Event;
import com.vtcsclubs.backend.services.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vtcsclubevents/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    /*
    * Get access to all events
    * */
    @GetMapping
    public List<EventResponse> getAllEvents(){
        return eventService.getAllEvents();
    }

    /*
    * Access a single event
    * */
    @GetMapping("{id}")
    public EventDetailsResponse getEventById(@PathVariable("id") Long id){
        return eventService.getEventById(id);
    }

    /*
    * Create a brand-new event
    * */
    @PostMapping("/createnewevent")
    public ResponseEntity<EventDetailsResponse> createEvent(@Valid @RequestBody CreateEventRequest request){
        EventDetailsResponse newEvent = eventService.createEvent(request);
        return new ResponseEntity<>(newEvent, HttpStatus.CREATED);
    }

    /*
    * Edit an event
    * */
    @PatchMapping("/updateevent/{id}")
    public ResponseEntity<EventDetailsResponse> updateEvent(@PathVariable("id") Long id, @Valid @RequestBody UpdateEventRequest request){
        EventDetailsResponse updatedEvent = eventService.updateEvent(id, request);
        return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
    }

    /*
    * Delete an event
    * */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEventById(@PathVariable("id") Long id){
        eventService.deleteEventById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
