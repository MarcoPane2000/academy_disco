package com.betacom.disco.controllers;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.betacom.disco.dtos.EventFilter;
import com.betacom.disco.dtos.EventResponseDto;
import com.betacom.disco.entities.Event;
import com.betacom.disco.mappers.EventMapper;
import com.betacom.disco.services.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    //
    
    @GetMapping("/all")
    public ResponseEntity<Page<EventResponseDto>> findAll(
            EventFilter filters,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        Page<Event> eventsPage = eventService.findAll(filters, page, size);
        return ResponseEntity.ok(eventsPage.map(eventMapper::toDto));
    }

    // localhost:8080/api/events/4
    @GetMapping("/{id}")
    public ResponseEntity<EventResponseDto> findById(@PathVariable Long id) {
        Optional<Event> event = eventService.findByid(id);
        if (event.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        EventResponseDto responseDto = eventMapper.toDto(event.get());
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<EventResponseDto> create(@RequestBody Event event) {
        Event newEvent = eventService.create(event);
        return ResponseEntity.status(201).body(eventMapper.toDto(newEvent));
    }

    @PostMapping("/{id}/upload-image")
    public ResponseEntity uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile[] images) {
        try {
            eventService.saveEventImages(id, images);
            return ResponseEntity.ok("Images uploaded succesfully.");
        } catch (IllegalArgumentException error) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error.getMessage());
        } catch (Exception error) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload images.");
        }
    }
}
