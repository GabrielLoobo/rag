package com.estudos.rag.infrastructure.question.payload.request;

import lombok.Builder;

@Builder
public record ExternalQuestionRequestContent(
    String role,
    String message) {
}
