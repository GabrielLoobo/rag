package com.estudos.rag.domain.enums;

public enum DocumentStatusEnum {
  UPLOADED,
  INDEXED,
  DELETED;

  public String getName() {
    return this.toString().toLowerCase();
  }
}
