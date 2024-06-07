package com.estudos.rag.infrastructure.auth.service;

import com.estudos.rag.domain.entity.Document;
import com.estudos.rag.infrastructure.auth.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentService {
  private final DocumentRepository documentRepository;

  public Document createDocument(Document newDocument) {
    log.debug("Saving created document to DB");
    return documentRepository.saveAndFlush(newDocument);
  }
}
