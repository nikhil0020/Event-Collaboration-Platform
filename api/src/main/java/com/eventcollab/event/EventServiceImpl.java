package com.eventcollab.event;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eventcollab.event.dto.EventRequest;
import com.eventcollab.event.dto.EventResponse;
import com.eventcollab.global.dto.PageResponse;
import com.eventcollab.global.exception.NotAuthorizedToAccessResourceException;
import com.eventcollab.participant.EventParticipant;
import com.eventcollab.participant.EventParticipantRepository;
import com.eventcollab.participant.ParticipantRole;
import com.eventcollab.security.CurrentUserService;
import com.eventcollab.user.User;
import com.eventcollab.user.UserRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService{
  
  private final EventRepository eventRepository;
  private final UserRepository userRepository;
  private final EventParticipantRepository eventParticipantRepository;
  private final CurrentUserService currentUserService;
  private final EventMapper eventMapper;
  
  @Override
  public EventResponse createEvent(@Valid EventRequest eventRequest) {
    Long currentUserId = currentUserService.getCurrentUserId();

    User user = userRepository.findById(currentUserId).orElseThrow(
      () -> new UsernameNotFoundException("User with userid " + currentUserId + " not found")
    );

    Event event = Event.builder()
                    .createdBy(user)
                    .title(eventRequest.getTitle())
                    .description(eventRequest.getDescription())
                    .startTime(eventRequest.getStartTime())
                    .endTime(eventRequest.getEndTime())
                    .location(eventRequest.getLocation())
                    .status(EventStatus.DRAFT)
                    .createdAt(LocalDateTime.now())
                    .build();
    Event savedEvent = eventRepository.save(event);

    EventParticipant eventParticipant = EventParticipant.builder()
                                              .event(savedEvent)
                                              .user(user)
                                              .role(ParticipantRole.HOST)
                                              .joinedAt(LocalDateTime.now())
                                              .build();
    eventParticipantRepository.save(eventParticipant);

    return eventMapper.toEventResponse(savedEvent);

  }

  @Override
  public EventResponse updateEvent(Long eventId, EventRequest updatedEvent) {
    Long currentUserId = currentUserService.getCurrentUserId();
    Event event = eventRepository.findById(eventId).orElseThrow(
      () -> new EventNotFoundException("Event not found with eventId: " + eventId)
    );

    if (!event.getCreatedById().equals(currentUserId)) {
      throw new NotAuthorizedToAccessResourceException("Update event with eventId: " + eventId );
    }

    eventMapper.updateEvent(updatedEvent, event);

    return eventMapper.toEventResponse(event);
  }

  @Override
  public void deleteEvent(Long eventId) {
    Long currentUserId = currentUserService.getCurrentUserId();
    Event event = eventRepository.findById(eventId).orElseThrow(
      () -> new EventNotFoundException("Event not found with eventId: " + eventId)
    );

    if (!event.getCreatedById().equals(currentUserId)) {
      throw new NotAuthorizedToAccessResourceException("delete event with eventId: " + eventId );
    }

    eventRepository.delete(event);
  }

  @Override
  public Optional<EventResponse> getById(Long id) {
    return eventRepository.findFirstByEventId(id);
  }

  @Override
  public PageResponse<EventResponse> listPublishedEvents(Pageable pageable) {
    Page<EventResponse> events = eventRepository.findByStatus(EventStatus.PUBLISHED, pageable);
    return getPageResponse(events);
  }

  @Override
  public EventResponse publishEvent(Long eventId) {
    Long currentUserId = currentUserService.getCurrentUserId();
    Event event = eventRepository.findById(eventId).orElseThrow(
      () -> new EventNotFoundException("Event not found with eventId: " + eventId)
    );

    if (!event.getCreatedById().equals(currentUserId)) {
      throw new NotAuthorizedToAccessResourceException("publish event with eventId: " + eventId );
    }

    event.setStatus(EventStatus.PUBLISHED);

    Event updatedEvent = eventRepository.save(event);
    return eventMapper.toEventResponse(updatedEvent);
  }

  @Override
  public PageResponse<EventResponse> searchPublisedEvents(String search, Pageable pageable) {

    Page<EventResponse> events;

    if (search == null || search.isBlank()) {
      events = eventRepository.findByStatus(EventStatus.PUBLISHED, pageable);
    } else {
      events = eventRepository.findByStatusAndTitleContainingIgnoreCase(EventStatus.PUBLISHED, search, pageable);
    }

    return getPageResponse(events);
  }

  public <T> PageResponse<T> getPageResponse(Page<T> pages) {
    return new PageResponse<>(
      pages.getContent(),
      pages.getNumber(),
      pages.getSize(),
      pages.getTotalElements(),
      pages.getTotalPages(),
      pages.isLast()
    );
  }
}
