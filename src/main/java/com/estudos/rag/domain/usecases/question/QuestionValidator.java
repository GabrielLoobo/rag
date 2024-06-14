package com.estudos.rag.domain.usecases.question;

import com.estudos.rag.application.question.payload.request.QuestionRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QuestionValidator {
  public void validateCreate(QuestionRequest request){
    // TODO: Adicionar verificação de prompts do usuário de acordo com plano
  }
}
