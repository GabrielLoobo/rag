package com.estudos.rag.infrastructure.question.payload.request;

import com.estudos.rag.application.question.payload.request.QuestionRequestContent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

import java.util.List;

@Builder
public record ExternalQuestionRequest(
    @Max(value = 1) @Min(value = 0) Float temperature,
    @Min(value = 0) Integer max_tokens,
    List<ExternalQuestionRequestContent> content) {
}
