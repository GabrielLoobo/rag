package com.estudos.rag.domain.usecases.question;

import com.estudos.rag.application.question.payload.request.QuestionRequest;
import com.estudos.rag.domain.entity.User;
import com.estudos.rag.infrastructure.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@AllArgsConstructor
public class QuestionValidator {
  UserService userService;

  public void validateCreate(QuestionRequest request){
    User user = userService.getUserWithAuthenticationContext();

    if (!user.canSubmitMorePrompts()) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Prompt limit reached");
    }
  }
}
