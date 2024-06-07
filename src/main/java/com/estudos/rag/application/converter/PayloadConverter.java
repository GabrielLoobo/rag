package com.estudos.rag.application.converter;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class PayloadConverter<T> {
  private PayloadMapper<T> mapper;

  public List<T> listPayloadToEntity(List<Record> payloadList) {
    if (payloadList == null) {
      return null;
    }
    return payloadList.stream().map(p -> mapper.payloadToEntity(p)).collect(Collectors.toList());
  }

  // TODO: responses could be better typed
  @SuppressWarnings("unchecked")
  public <P extends Record> List<P> listEntityToPayload(List<T> entityList,
      Class<P> payloadClassName) {
    if (entityList == null) {
      return null;
    }
    return entityList.stream().map(e -> (P) mapper.entityToListPayload(e))
        .collect(Collectors.toList());
  }

  public <P extends Record> Page<P> pageEntityToPayload(Page<T> entityPage,
      Class<P> payloadClassName) {
    if (entityPage == null) {
      return null;
    }
    return new PageImpl<P>(listEntityToPayload(entityPage.getContent(), payloadClassName),
        entityPage.getPageable(),
        entityPage.getTotalElements());
  }
}
