package com.eventcollab.event;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.eventcollab.event.dto.EventRequest;
import com.eventcollab.event.dto.EventResponse;
import com.eventcollab.global.dto.PageResponse;

public interface EventService {
  EventResponse createEvent(EventRequest event);

  EventResponse updateEvent(Long eventId, EventRequest updatedEvent);

  void deleteEvent(Long eventId);

  Optional<EventResponse> getById(Long id);

  PageResponse<EventResponse> listPublishedEvents(Pageable pageable);

  EventResponse publishEvent(Long eventId);

  PageResponse<EventResponse> searchPublisedEvents(String search, Pageable pageable);
}
