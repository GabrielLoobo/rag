package com.estudos.rag.application.question.payload.response;

import lombok.Builder;

@Builder
public record QuestionReponseUsage(
    Integer prompt_tokens,
    Integer response_tokens) {
}
