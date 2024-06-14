package com.estudos.rag.infrastructure.question.payload.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ExternalQuestionResult (
    String id,
    String role,
    String model,
    String stop_reason,
    ExternalQuestionResultUsage usage,
    List<ExternalQuestionResultContent> content){
}
