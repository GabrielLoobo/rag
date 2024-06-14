package com.estudos.rag.infrastructure.question.services;

import com.estudos.rag.infrastructure.question.payload.request.ExternalQuestionRequest;
import com.estudos.rag.infrastructure.question.payload.response.ExternalQuestionResult;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "question")
public interface QuestionClient {

  @PostMapping("/chat/question")
  ExternalQuestionResult ask(
      @Valid @RequestBody ExternalQuestionRequest request);

}
