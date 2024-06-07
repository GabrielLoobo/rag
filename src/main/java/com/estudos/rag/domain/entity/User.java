package com.estudos.rag.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

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
}
