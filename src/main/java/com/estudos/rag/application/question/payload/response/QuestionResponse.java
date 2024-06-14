package com.estudos.rag.application.question.payload.response;

import lombok.Builder;

import java.util.List;

@Builder
public record QuestionResponse(
    String id,
    String role,
    String model,
    String stopReason,
    QuestionReponseUsage usage,
    List<QuestionResponseContent> content) {
}
