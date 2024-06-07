package com.estudos.rag.application.document.converter;

import com.estudos.rag.application.document.payload.response.DocumentResponse;
import com.estudos.rag.application.converter.PayloadConverter;
import com.estudos.rag.domain.entity.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentConverter extends PayloadConverter<Document> {
  private final DocumentMapper mapper;

  public DocumentConverter(DocumentMapper mapper) {
    super(mapper);
    this.mapper = mapper;
  }

  public DocumentResponse entityToPayload(Document document) {
    return mapper.entityToPayload(document);
  }
}
