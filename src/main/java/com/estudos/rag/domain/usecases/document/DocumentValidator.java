package com.estudos.rag.domain.usecases.document;

import com.estudos.rag.application.document.payload.request.DocumentUploadRequest;
import com.estudos.rag.infrastructure.auth.repository.DocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component
@AllArgsConstructor
public class DocumentValidator {
  private final DocumentRepository documentRepository;

  public void validateCreate(DocumentUploadRequest request, String fileChecksum) {
    // TODO: Verificar plano do usuário para: número de arquivos permitidos

    if (documentRepository.existsByHash(fileChecksum)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate file");
    }
  }


}
