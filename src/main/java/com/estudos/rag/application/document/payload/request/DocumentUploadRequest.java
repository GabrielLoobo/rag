package com.estudos.rag.application.document.payload.request;

import com.estudos.rag.application.document.payload.validator.ValidDocument;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record DocumentUploadRequest(
  @ValidDocument MultipartFile file,
  String name
) {
}
