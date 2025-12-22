package com.eventcollab.event;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.eventcollab.event.dto.EventRequest;
import com.eventcollab.event.dto.EventResponse;

@Mapper(componentModel = "spring")
public interface EventMapper {
  Event toEvent(EventRequest request);
  EventResponse toEventResponse(Event event);
  void updateEvent(EventRequest request, @MappingTarget Event event);
}
