package com.eventcollab.event;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventcollab.event.dto.EventRequest;
import com.eventcollab.event.dto.EventResponse;
import com.eventcollab.global.dto.PageResponse;
import com.eventcollab.global.exception.ResourceNotFoundException;
import com.eventcollab.participant.EventParticipantService;
import com.eventcollab.user.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
  private final EventService eventService;
  private final EventParticipantService eventParticipantService;
  private final UserRepository userRepository;

  @GetMapping
  public PageResponse<EventResponse> listEvents(
    @RequestParam(value = "search", required = false) String search,
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = "10") int size
  ) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("startTime").descending());

    return eventService.searchPublisedEvents(search, pageable);
  }

  @GetMapping("/{id}")
  public ResponseEntity<EventResponse> getEvent(
    @PathVariable Long id
  ) {
    EventResponse res = eventService
                          .getById(id)
                          .orElseThrow(() -> new ResourceNotFoundException("Event", "id", id));
    return ResponseEntity.ok(res);
  }

  @PostMapping
  public ResponseEntity<EventResponse> createEvent(
    @Valid @RequestBody EventRequest request
  ) {
    return ResponseEntity.ok(eventService.createEvent(request));
  }
}
