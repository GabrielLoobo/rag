package com.estudos.rag.application.question.payload.response;

import lombok.Builder;

@Builder
public record QuestionResponseContent(
    String type,
    String message) {
}
