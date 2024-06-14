package com.estudos.rag.application.question.converter;

import com.estudos.rag.application.converter.PayloadMapper;
import com.estudos.rag.application.question.payload.response.QuestionResponse;
import com.estudos.rag.domain.models.QuestionResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionMapper extends PayloadMapper<QuestionResult> {
  @Override
  QuestionResponse entityToPayload(QuestionResult entity);

//  @Override
  QuestionResponse entityToListPayload(QuestionResult entity);
}
