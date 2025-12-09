package com.eventcollab.participant;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long>{
  List<EventParticipant> findByEventId(Long eventId);

  Optional<EventParticipant> findByEventIdAndUserId(Long eventId, Long userId);

  Long countByEventId(Long eventId);

  void deleteByEventIdAndUserId(Long eventId, Long userId);
}
