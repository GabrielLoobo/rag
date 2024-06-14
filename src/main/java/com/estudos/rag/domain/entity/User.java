package com.estudos.rag.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String username;

  private String name;

  private String socialAuthId;

  @Builder.Default
  private Integer status = 0;

  private Timestamp lastQueriedAt;

  @Builder.Default
  private Integer queryCount = 0;

  @ManyToOne
  @JoinColumn(name = "membership_plan_id", nullable = false)
  private MembershipPlan membershipPlan;

  public void handlePromptSubmitCount() {
    if(this.getLastQueriedAt() == null) {
      setLastQueriedAtToNow();
      this.setQueryCount(1);
    } else {
      Instant now = Instant.now();
      Instant lastQueriedAtInstant = this.getLastQueriedAt().toInstant();

      Duration durationBetweenLastSubmit = Duration.between(now, lastQueriedAtInstant);
      if (durationBetweenLastSubmit.toHours() > 24) {
        this.setQueryCount(1);
      } else{
        this.setQueryCount(
            this.getQueryCount() + 1
        );
      }
      setLastQueriedAtToNow();
    }
  }

  public Boolean canSubmitMorePrompts(){
    return this.getMembershipPlan().getDailyPromptLimit() > this.queryCount;
  }

  private void setLastQueriedAtToNow() {
    this.setLastQueriedAt(
        Timestamp.from(Instant.now())
    );
  }
}
