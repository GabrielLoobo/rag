package com.estudos.rag.infrastructure.question.services;

import com.estudos.rag.application.question.payload.request.QuestionRequest;
import com.estudos.rag.domain.models.QuestionResult;
import com.estudos.rag.domain.models.QuestionResultContent;
import com.estudos.rag.domain.models.QuestionResultUsage;
import com.estudos.rag.infrastructure.question.payload.request.ExternalQuestionRequest;
import com.estudos.rag.infrastructure.question.payload.request.ExternalQuestionRequestContent;
import com.estudos.rag.infrastructure.question.payload.response.ExternalQuestionResult;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class QuestionService {
  private final QuestionClient client;

  public QuestionResult ask(QuestionRequest request) {
    ExternalQuestionRequest externalQuestionRequest  = convertFromDomain(request);

    ExternalQuestionResult response = client.ask(externalQuestionRequest);

    return convertToDomain(response);
  }

  private ExternalQuestionRequest convertFromDomain(QuestionRequest request) {
    ExternalQuestionRequestContent contentItem = ExternalQuestionRequestContent.builder()
        .role(request.content().role())
        .message(request.content().message())
        .build();

    return ExternalQuestionRequest.builder()
        .temperature(request.temperature())
        .max_tokens(request.max_tokens())
        .content(Arrays.asList(contentItem))
        .build();
  }

  private QuestionResult convertToDomain(ExternalQuestionResult externalQuestionResult) {
    QuestionResult questionResult = new QuestionResult();
    BeanUtils.copyProperties(externalQuestionResult, questionResult);

    QuestionResultUsage usage = new QuestionResultUsage();
    BeanUtils.copyProperties(externalQuestionResult.usage(), usage);
    questionResult.setUsage(usage);

    externalQuestionResult.content().forEach(c -> {
      QuestionResultContent questionResultContent = new QuestionResultContent();
      BeanUtils.copyProperties(c, questionResultContent);
      questionResult.getContent().add(questionResultContent);
    });

    return questionResult;
  }
}
