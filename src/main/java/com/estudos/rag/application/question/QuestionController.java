package com.estudos.rag.application.question;

import com.estudos.rag.application.question.converter.QuestionConverter;
import com.estudos.rag.application.question.payload.request.QuestionRequest;
import com.estudos.rag.application.question.payload.response.QuestionResponse;
import com.estudos.rag.domain.usecases.question.QuestionUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat/question")
@RequiredArgsConstructor
public class QuestionController {
  private final QuestionConverter converter;
  private final QuestionUseCase useCase;

  @PostMapping
  public ResponseEntity<QuestionResponse> ask(
      @Valid @RequestBody QuestionRequest request) {

    QuestionResponse response = converter.entityToPayload(useCase.ask(request));

    return ResponseEntity.ok(response);
  }
}
