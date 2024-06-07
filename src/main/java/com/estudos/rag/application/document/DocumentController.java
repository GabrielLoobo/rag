package com.estudos.rag.application.document;

import com.estudos.rag.application.document.converter.DocumentConverter;
import com.estudos.rag.application.document.payload.request.DocumentUploadRequest;
import com.estudos.rag.application.document.payload.response.DocumentResponse;
import com.estudos.rag.domain.usecases.document.DocumentUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {
  private final DocumentUseCase useCase;
  private final DocumentConverter converter;

  @PostMapping
  public ResponseEntity<DocumentResponse> upload(@ModelAttribute @Valid DocumentUploadRequest documentUploadRequest) {
    DocumentResponse fileResponse = converter.entityToPayload(useCase.upload(documentUploadRequest));

    // TODO: Adicionar path do storage após upload
    return ResponseEntity.created(URI.create("MOCKEDPATH")).build();
  }
}
