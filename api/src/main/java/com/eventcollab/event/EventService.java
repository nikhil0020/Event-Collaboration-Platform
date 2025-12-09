package com.eventcollab.event;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EventService {
  Event createEvent(Event event);

  Event updateEvent(Long eventId, Event updatedEvent, Long currentUserId);

  void deleteEvent(Long eventId, Long currentUserId);

  Optional<Event> getById(Long id);

  Page<Event> listPublishedEvents(Pageable pageable);

  Event publishEvent(Long eventId, Long currentUserId);
}
