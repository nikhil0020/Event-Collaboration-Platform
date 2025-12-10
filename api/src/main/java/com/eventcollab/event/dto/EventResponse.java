package com.eventcollab.event.dto;

import java.time.LocalDateTime;

public record EventResponse(
  Long id,
  String title,
  String description,
  String location,
  LocalDateTime starTime,
  LocalDateTime endTime
) {
  
}