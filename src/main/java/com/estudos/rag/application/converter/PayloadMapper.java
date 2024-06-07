package com.estudos.rag.application.converter;

public interface PayloadMapper<T> {
  public T payloadToEntity(Record payload);

  public Record entityToPayload(T entity);

  public Record entityToListPayload(T entity);
}
