package com.estudos.rag.infrastructure.document.service;

import com.estudos.rag.domain.entity.Document;
import com.estudos.rag.domain.entity.User;
import com.estudos.rag.domain.filters.DocumentFilter;
import com.estudos.rag.infrastructure.document.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentService {
  private final DocumentRepository documentRepository;

  public Document createDocument(Document newDocument) {
    log.debug("Saving created document to DB");
    return documentRepository.saveAndFlush(newDocument);
  }

  public Page<Document> findAllByUser(User user, Pageable pageable) {

    DocumentFilter documentFilter = DocumentFilter.builder()
        .userId(user.getId())
        .build();

    log.debug("Listing documents by user from DB");
    return documentRepository.findAll(documentFilter.get(), pageable);
  }

  public Optional<Document> findByIdAndUserId(Long documentId, Long userId) {
    log.debug("Retrieving document by user from DB");
    return documentRepository.findByIdAndUserId(documentId, userId);
  }

  public void delete(Document document) {
    log.debug("Deleting document  from DB");
    documentRepository.deleteById(document.getId());
  }
}
