package com.estudos.rag.domain.entity;

import com.estudos.rag.domain.enums.DocumentStatusEnum;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Document {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String hash;

  private String name;

  private Long size;

  private String storagePath;

  private Long userId;

  private DocumentStatusEnum status;
}
