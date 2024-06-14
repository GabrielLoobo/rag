package com.estudos.rag.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class QuestionResultUsage {
  public Integer prompt_tokens;
  public Integer result_tokens;
}
