package com.example.codeconnect.answer.service;

import com.example.codeconnect.answer.controller.DTO.AnswerRequest;
import com.example.codeconnect.answer.controller.DTO.AnswerResponse;

public interface AnswerService {
    AnswerResponse createAnswer(Long postId, AnswerRequest answerRequest);
    AnswerResponse modifyAnswer(Long answerId, AnswerRequest answerRequest);
    void deleteAnswer(Long answerId);
}
