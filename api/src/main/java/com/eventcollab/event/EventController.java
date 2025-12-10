package com.eventcollab.event;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventcollab.participant.EventParticipantService;
import com.eventcollab.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {
  private final EventService eventService;
  private final EventParticipantService eventParticipantService;
  private final UserRepository userRepository;

  // @GetMapping
  // public Page<EventResponse> listEvents(

  // ) {

  // }
}
