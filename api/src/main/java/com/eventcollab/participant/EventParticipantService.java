package com.eventcollab.participant;

import java.util.List;

public interface EventParticipantService {
  EventParticipant joinEvent(Long eventId);
  
  List<EventParticipant> listParticipants(Long eventId);

  boolean isUserJoined(Long eventId, Long userId);

  void leaveEvent(Long eventId);
}
