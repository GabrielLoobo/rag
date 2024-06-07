package com.estudos.rag.application.document.payload.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class DocumentValidator implements ConstraintValidator<ValidDocument, MultipartFile> {
  // TODO: Validar se parâmetro do tamanho do arquivo deve vir do plano
  private static final long MAX_FILE_SIZE_BYTES = 50 * 1024 * 1024;
  @Override
  public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
    if (file == null || file.isEmpty()) {
      return false;
    }
    if (file.getSize() > MAX_FILE_SIZE_BYTES) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Tamanho do arquivo excede o limite permitido").addConstraintViolation();
      return false;
    }

    if (!isContentTypeSupported(file.getContentType())) {
      context.disableDefaultConstraintViolation();
      context.buildConstraintViolationWithTemplate("Formato de arquivo não suportado").addConstraintViolation();
      return false;
    }

    return true;
  }

  private boolean isContentTypeSupported(String contentType) {
    // Ref iana.org/assignments/media-types/media-types.xhtml
    List<String> supportedContentTypes = List.of(
        " application/pdf"
    );

    return supportedContentTypes.contains(contentType);
  }
}

