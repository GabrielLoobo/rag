package com.estudos.rag.infrastructure.document.payload.request;

import com.estudos.rag.infrastructure.document.enums.DocumentUpdateQueueStatusEnum;
import lombok.Builder;

@Builder
public record DocumenteUpdateQueueMessage(
    Long documentId,
    DocumentUpdateQueueStatusEnum status
) {
}
