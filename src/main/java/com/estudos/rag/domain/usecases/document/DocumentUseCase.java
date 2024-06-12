package com.estudos.rag.domain.usecases.document;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobStorageException;
import com.estudos.rag.domain.entity.Document;
import com.estudos.rag.domain.entity.User;
import com.estudos.rag.domain.enums.DocumentStatusEnum;
import com.estudos.rag.infrastructure.document.service.DocumentQueueService;
import com.estudos.rag.infrastructure.document.service.DocumentService;
import com.estudos.rag.infrastructure.auth.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import com.estudos.rag.application.document.payload.request.DocumentUploadRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class DocumentUseCase {
  private final BlobServiceClient blobServiceClient;
  private final DocumentService documentService;
  private final DocumentQueueService documentQueueService;
  private final UserService userService;
  private final DocumentValidator validator;

  public Page<Document> getPaginatedByUser(Pageable pageable) {
    User user = userService.getUserWithAuthenticationContext();

    return documentService.findAllByUser(user, pageable);
  }

  public Document upload(DocumentUploadRequest documentUploadRequest) {
    String fileChecksum = calculateFileChecksum(documentUploadRequest.file());

    validator.validateCreate(documentUploadRequest, fileChecksum);

    User user = userService.getUserWithAuthenticationContext();
    String blobFileUrl = uploadDocumentToStorage(
        documentUploadRequest.file(),
        fileChecksum,
        user.getId(),
        "documents");

    Document document = createDocument(
        documentUploadRequest.file(),
        fileChecksum,
        documentUploadRequest.name(),
        user.getId(),
        blobFileUrl);

    // TODO: Validar em que momento atualizamos o status de indexed para o arquivo
    documentQueueService.sendDocumentCreateMessage(document.getId());

    return document;
  }

  public void deleteFile(Long documentId) {
    User user = userService.getUserWithAuthenticationContext();
    Optional<Document> optDocument = documentService.findByIdAndUserId(documentId, user.getId());

    if (optDocument.isPresent()) {
      Document document = optDocument.get();
      
      deleteDocumentFromStorage(document.getHash(), user.getId(), "documents");

      documentService.delete(document);

      documentQueueService.sendDocumentDeleteMessage(document.getId());
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }



  }

  private String uploadDocumentToStorage(MultipartFile file, String documentChecksum, Long userId, String containerName) {

    try {
      BlobClient blobClient =
          blobServiceClient
              .getBlobContainerClient(containerName)
              .getBlobClient(String.format("%d/%s", userId, documentChecksum));
      blobClient.upload(file.getInputStream(), false);

      return blobClient.getBlobUrl();
    } catch (BlobStorageException | IOException ex) {
      log.error("Error when uploading file", ex);
      throw new RuntimeException("Error during file upload");
    }
  }

  private Document createDocument(
      MultipartFile file,
      String fileChecksum,
      String name,
      Long userId,
      String blobFileUrl) {
    String originalFileName = file.getOriginalFilename();
    String extension = FilenameUtils.getExtension(originalFileName);

    String fullFileName = String.join(".", name, extension);

    Document document = Document.builder()
        .name(fullFileName)
        .size(file.getSize())
        .storagePath(blobFileUrl)
        .userId(userId)
        .status(DocumentStatusEnum.UPLOADED)
        .hash(fileChecksum)
        .build();

    return documentService.createDocument(document);
  }

  private void deleteDocumentFromStorage(String documentChecksum, Long userId, String containerName) {
    try {
      BlobClient blobClient =
          blobServiceClient
              .getBlobContainerClient(containerName)
              .getBlobClient(String.format("%d/%s", userId, documentChecksum));
      blobClient.delete();

    } catch (BlobStorageException ex) {
      log.error("Error when deleting file", ex);
      throw new RuntimeException("Error during file delete");
    }
  }

  private String calculateFileChecksum(MultipartFile file) {
    try {
      byte[] data = file.getBytes();
      byte[] hash = MessageDigest.getInstance("MD5").digest(data);

      return new BigInteger(1, hash).toString(16);
    } catch (Exception exception) {
      log.error("Error while generating file hash", exception);
      throw new RuntimeException("Error while generating file hash");
    }
  }
}
