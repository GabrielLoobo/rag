package com.estudos.rag.infrastructure.question.payload.response;

import lombok.Builder;

@Builder
public record ExternalQuestionResultContent (
    String type,
    String message){
}
