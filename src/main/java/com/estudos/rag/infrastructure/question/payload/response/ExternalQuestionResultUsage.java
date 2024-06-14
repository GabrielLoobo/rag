package com.estudos.rag.infrastructure.question.payload.response;

import lombok.Builder;

@Builder
public record ExternalQuestionResultUsage (
    Integer prompt_tokens,
    Integer result_tokens) {
}
