package com.estudos.rag.infrastructure.document.service;

import com.estudos.rag.infrastructure.document.enums.DocumentUpdateQueueStatusEnum;
import com.estudos.rag.infrastructure.document.payload.request.DocumenteUpdateQueueMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DocumentQueueService {
  @Autowired
  AmqpTemplate amqpTemplate;

  static final String exchangeName = "document-indexing-exchange"; // TODO: Passar pra application.yml

  static final String bindingName = "document-indexing";

  private final ObjectMapper objectMapper = new ObjectMapper();

  public void sendDocumentCreateMessage(Long documentId) {
    DocumenteUpdateQueueMessage message = DocumenteUpdateQueueMessage.builder()
        .documentId(documentId)
        .status(DocumentUpdateQueueStatusEnum.CREATED)
        .build();

    sendDocumentUpdateMessage(message);
  }

  public void sendDocumentDeleteMessage(Long documentId) {
    DocumenteUpdateQueueMessage message = DocumenteUpdateQueueMessage.builder()
        .documentId(documentId)
        .status(DocumentUpdateQueueStatusEnum.DELETED)
        .build();

    sendDocumentUpdateMessage(message);
  }

  private void sendDocumentUpdateMessage(DocumenteUpdateQueueMessage documenteUpdateQueueMessage) {
    try {
      amqpTemplate.convertAndSend(
          exchangeName,
          bindingName,
          objectMapper.writeValueAsString(documenteUpdateQueueMessage)
      );
    } catch (JsonProcessingException ex) {
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while submiting file to document indexing queue");
    }
  }
}
