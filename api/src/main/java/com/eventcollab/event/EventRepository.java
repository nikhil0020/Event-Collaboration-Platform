package com.eventcollab.event;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eventcollab.event.dto.EventResponse;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{

  @Query("SELECT event FROM Event event WHERE event.id = :id")
  Optional<EventResponse> findFirstByEventId(@Param("id") Long id);

  Page<EventResponse> findByStatus(EventStatus status, Pageable pageable);

  Page<EventResponse> findByStatusAndTitleContainingIgnoreCase(EventStatus status, String title, Pageable pageable);
}
