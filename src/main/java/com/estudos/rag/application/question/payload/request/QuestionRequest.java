package com.estudos.rag.application.question.payload.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record QuestionRequest(
    @Max(value = 1) @Min(value = 0) Float temperature,
    @Min(value = 0) Integer max_tokens,
    QuestionRequestContent content ) {
}
