package com.estudos.rag.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QuestionResult {
  public String id;
  public String role;
  public String model;
  public String stop_reason;
  QuestionResultUsage usage;
  List<QuestionResultContent> content;
}
