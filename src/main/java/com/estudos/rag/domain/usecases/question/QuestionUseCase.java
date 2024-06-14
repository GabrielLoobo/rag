package com.estudos.rag.domain.usecases.question;

import com.estudos.rag.application.question.payload.request.QuestionRequest;
import com.estudos.rag.domain.entity.User;
import com.estudos.rag.domain.models.QuestionResult;
import com.estudos.rag.infrastructure.auth.service.UserService;
import com.estudos.rag.infrastructure.question.services.QuestionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class QuestionUseCase {
  private final QuestionValidator validator;
  private final QuestionService questionService;
  private final UserService userService;

  public QuestionResult ask(QuestionRequest request) {
    validator.validateCreate(request);

    QuestionResult result = questionService.ask(request);

    userService.handlePromptSubmitCount();

    return result;
  }
}
