package com.estudos.rag.domain.usecases.document;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobStorageException;
import com.estudos.rag.domain.entity.Document;
import com.estudos.rag.domain.entity.User;
import com.estudos.rag.domain.enums.DocumentStatusEnum;
import com.estudos.rag.infrastructure.auth.service.DocumentService;
import com.estudos.rag.infrastructure.auth.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import com.estudos.rag.application.document.payload.request.DocumentUploadRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@AllArgsConstructor
@Slf4j
public class DocumentUseCase {
  private final BlobServiceClient blobServiceClient;
  private final DocumentService documentService;
  private final UserService userService;

  public Document upload(DocumentUploadRequest documentUploadRequest) {
    /*  Upload do arquivo no storage */
    // TODO: Verificar plano do usuário para: número de arquivos permitidos
    User user = userService.getUserWithAuthenticationContext();
    String blobFileUrl = uploadDocumentToStorage(
        documentUploadRequest.file(),
        documentUploadRequest.name(),
        "documents");

    /* Subir mensagem na fila para indexação */
    // TODO: Validar em que momento atualizamos o status de indexed para o arquivo

    /* Criar registro local com informações do arquivo carregado */
    Document document = createDocument(
        documentUploadRequest.file(),
        documentUploadRequest.name(),
        user.getId(),
        blobFileUrl);

    return document;
  }

  private String uploadDocumentToStorage(MultipartFile file, String fileName, String containerName) {
    String originalFileName = file.getOriginalFilename();
    String extension = FilenameUtils.getExtension(originalFileName);

    try {
      BlobClient blobClient =
          blobServiceClient
              .getBlobContainerClient(containerName)
              .getBlobClient(String.format("%s.%s", fileName, extension)); // TODO: Incluir hash no identificador da imagem
      blobClient.upload(file.getInputStream(), false);

      return blobClient.getBlobUrl();
    } catch (BlobStorageException | IOException ex) {
      log.error("Error when uploading file", ex);
      throw new RuntimeException("Error during file upload");
    }
  }

  private Document createDocument(
      MultipartFile file,
      String name,
      Long userId,
      String blobFileUrl) {
    String originalFileName = file.getOriginalFilename();
    String extension = FilenameUtils.getExtension(originalFileName);


    // TODO: Adicionar hash para os arquivos
    String fullFileName = String.join(".", name, extension);

    Document document = Document.builder()
        .name(fullFileName)
        .size(file.getSize())
        .storagePath(blobFileUrl)
        .userId(userId)
        .status(DocumentStatusEnum.UPLOADED)
        .build();

    return documentService.createDocument(document);
  }
}
