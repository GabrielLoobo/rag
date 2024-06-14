package com.estudos.rag.application.question.payload.request;

import lombok.Builder;

@Builder
public record QuestionRequestContent(
    String role,
    String message) {
}
