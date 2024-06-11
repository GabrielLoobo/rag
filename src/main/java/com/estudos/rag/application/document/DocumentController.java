package com.estudos.rag.application.document;

import com.estudos.rag.application.document.converter.DocumentConverter;
import com.estudos.rag.application.document.payload.request.DocumentUploadRequest;
import com.estudos.rag.application.document.payload.response.DocumentResponse;
import com.estudos.rag.domain.usecases.document.DocumentUseCase;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DocumentController {
  private final DocumentUseCase useCase;
  private final DocumentConverter converter;

  @PostMapping
  public ResponseEntity<String> upload(@ModelAttribute @Valid DocumentUploadRequest documentUploadRequest) {
    DocumentResponse fileResponse = converter.entityToPayload(useCase.upload(documentUploadRequest));

    return ResponseEntity.created(URI.create(fileResponse.storagePath())).build();
  }

  @GetMapping
  @PageableAsQueryParam
  public ResponseEntity<Page<DocumentResponse>> list(
      @Parameter(hidden = true) Pageable pageable) {

    Page<DocumentResponse> result = converter.pageEntityToPayload(
        useCase.getPaginatedByUser(pageable),
        DocumentResponse.class);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    useCase.deleteFile(id);

    return ResponseEntity.noContent().build();
  }
}
