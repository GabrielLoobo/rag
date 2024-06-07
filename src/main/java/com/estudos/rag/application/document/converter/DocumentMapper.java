package com.estudos.rag.application.document.converter;

import com.estudos.rag.application.document.payload.response.DocumentResponse;
import com.estudos.rag.application.converter.PayloadMapper;
import com.estudos.rag.domain.entity.Document;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentMapper extends PayloadMapper<Document> {

  @Override
  DocumentResponse entityToPayload(Document entity);

  @Override
  DocumentResponse entityToListPayload(Document entity);
}
