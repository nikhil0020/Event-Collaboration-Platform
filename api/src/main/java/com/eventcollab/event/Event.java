package com.eventcollab.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.eventcollab.participant.EventParticipant;
import com.eventcollab.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Event {
  
  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length=250)
  private String title;

  @Column(columnDefinition="TEXT")
  private String description;

  private String location;

  private LocalDateTime startTime;

  private LocalDateTime endTime;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 30)
  @Builder.Default
  private EventStatus status = EventStatus.DRAFT;

  @Column(name = "created_by_id")
  @Setter(AccessLevel.NONE)
  private Long createdById;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by_id", insertable = false, updatable = false)
  @JsonIgnore
  private User createdBy;

  private LocalDateTime createdAt;

  @OneToMany(
    mappedBy = "event",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @Builder.Default
  private List<EventParticipant> participants = new ArrayList<>();

  public void setCreatedBy(User createdBy) {
    this.createdById = createdBy.getId();
    this.createdBy = createdBy;
  }
  
  @PrePersist
  public void prePersist() {
    if (createdAt == null) createdAt = LocalDateTime.now();
    if (status == null) status = EventStatus.DRAFT;
  }

  // Helper

  public void addParticipant(EventParticipant p) {
    participants.add(p);
    p.setEvent(this);
  }

  public void removeParticipant(EventParticipant p) {
    participants.remove(p);
    p.setEvent(null);
  }

}
