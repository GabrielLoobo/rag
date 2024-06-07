package com.estudos.rag.application.document.payload.response;

import com.estudos.rag.domain.enums.DocumentStatusEnum;
import lombok.Builder;

@Builder
public record DocumentResponse(
    Long id,
    String hash,
    String name,
    Long size,
    String storagePath,
    Long userId,
    DocumentStatusEnum status
) {
}
