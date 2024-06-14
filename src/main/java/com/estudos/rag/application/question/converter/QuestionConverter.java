package com.estudos.rag.application.question.converter;

import com.estudos.rag.application.converter.PayloadConverter;
import com.estudos.rag.application.question.payload.response.QuestionResponse;
import com.estudos.rag.domain.models.QuestionResult;
import org.springframework.stereotype.Component;

@Component
public class QuestionConverter extends PayloadConverter<QuestionResult> {
  private final QuestionMapper mapper;

  QuestionConverter(QuestionMapper mapper) {
    super(mapper);
    this.mapper = mapper;
  }

  public QuestionResponse entityToPayload(QuestionResult questionResult) {
    return mapper.entityToPayload(questionResult);
  }
}
