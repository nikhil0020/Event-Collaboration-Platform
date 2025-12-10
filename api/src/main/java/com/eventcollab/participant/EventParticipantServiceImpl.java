package com.eventcollab.participant;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eventcollab.event.Event;
import com.eventcollab.event.EventRepository;
import com.eventcollab.security.CurrentUserService;
import com.eventcollab.user.User;
import com.eventcollab.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class EventParticipantServiceImpl implements EventParticipantService{

  private final EventParticipantRepository eventParticipantRepository;
  private final EventRepository eventRepository;
  private final UserRepository userRepository;
  private final CurrentUserService currentUserService;
  
  @Override
  public EventParticipant joinEvent(Long eventId) {
    Long currentUserId = currentUserService.getCurrentUserId();

    Event event = eventRepository.findById(eventId).orElseThrow(
      () -> new IllegalArgumentException("Event with id " + eventId + " not found")
    );

    User user = userRepository.findById(currentUserId).orElseThrow(
      () -> new UsernameNotFoundException("User with id " + currentUserId + " not found")
    );

    return eventParticipantRepository.findByEventIdAndUserId(eventId, user.getId()).orElseGet(() -> {
      EventParticipant eventParticipant = EventParticipant.builder()
                                            .event(event)
                                            .user(user)
                                            .role(ParticipantRole.ATTENDEE)
                                            .joinedAt(java.time.LocalDateTime.now())
                                            .build();
      return eventParticipantRepository.save(eventParticipant);
    });

  }

  @Override
  public List<EventParticipant> listParticipants(Long eventId) {
    return eventParticipantRepository.findByEventId(eventId);
  }

  @Override
  public boolean isUserJoined(Long eventId, Long userId) {
    return eventParticipantRepository.findByEventIdAndUserId(eventId, userId).isPresent();
  }

  @Override
  public void leaveEvent(Long eventId) {
    eventParticipantRepository.findByEventIdAndUserId(eventId, currentUserService.getCurrentUserId())
      .ifPresent(eventParticipantRepository::delete);
  }
  
}
