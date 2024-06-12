package com.estudos.rag.infrastructure.document.enums;

public enum DocumentUpdateQueueStatusEnum {
  CREATED,
  DELETED;

  public String getName() {
    return this.toString().toLowerCase();
  }
}
