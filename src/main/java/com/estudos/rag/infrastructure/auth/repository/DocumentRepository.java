package com.estudos.rag.infrastructure.auth.repository;

import com.estudos.rag.domain.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>,
    JpaSpecificationExecutor<Document> {
  Boolean existsByHash(String hash);

  Optional<Document> findByIdAndUserId(Long id, Long userId);
}
