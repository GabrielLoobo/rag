package com.estudos.rag.domain.usecases.document;

import com.estudos.rag.application.document.payload.request.DocumentUploadRequest;
import com.estudos.rag.domain.entity.User;
import com.estudos.rag.infrastructure.auth.service.UserService;
import com.estudos.rag.infrastructure.document.repository.DocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;


@Component
@AllArgsConstructor
public class DocumentValidator {
  private final DocumentRepository documentRepository;
  private final UserService userService;

  public void validateCreate(DocumentUploadRequest request, String fileChecksum) {
    User user = userService.getUserWithAuthenticationContext();
    if (user.getMembershipPlan().getDocumentUploadLimit() <= documentRepository.countByUserId(user.getId())) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You have already uploaded the maximmum amount of files allowed by your plan");
    }

    if (documentRepository.existsByHash(fileChecksum)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Duplicate file");
    }
  }


}
