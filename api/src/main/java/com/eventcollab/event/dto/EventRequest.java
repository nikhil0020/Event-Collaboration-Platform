package com.eventcollab.event.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRequest {
  @NotBlank(message = "Title is required")
  private String title;

  @NotBlank(message = "Description is required")
  @Min(value = 100, message = "Description must be at least 100 characters long")
  private String description;

  private String location;

  @NotNull
  private LocalDateTime startTime;

  @NotNull
  private LocalDateTime endTime;
}
