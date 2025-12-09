package com.eventcollab.event;

import org.springframework.boot.data.autoconfigure.web.DataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
  Page<Event> findByStatus(EventStatus status, Pageable pageable);

  Page<Event> findByStatusAndTitleContainingIgnoreCase(EventStatus status, String title, Pageable pageable);
}
