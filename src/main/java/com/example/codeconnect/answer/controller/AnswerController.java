package com.example.codeconnect.answer.controller;

import com.example.codeconnect.answer.controller.DTO.AnswerRequest;
import com.example.codeconnect.answer.controller.DTO.AnswerResponse;
import com.example.codeconnect.answer.service.AnswerServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
public class AnswerController {

    private final AnswerServiceImpl answerService;

    /**
     * 댓글 등록 기능
     * @param postId
     * @param answerRequest
     * @return AnswerRequest 저장 후 answerResponse 객체 반환
     */
    @PostMapping("/api/answers/{postId}")
    public ResponseEntity<AnswerResponse> createAnswer(@PathVariable("postId") Long postId, @Valid @RequestBody AnswerRequest answerRequest) {
        AnswerResponse answerResponse = answerService.createAnswer(postId, answerRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(answerResponse);
    }

    /**
     * 댓글 수정 기능
     * @param answerId
     * @param answerRequest
     * @return answerRequest저장 후 AnswerResponse 객체 반환
     */
    @PatchMapping("/api/answers/{answerId}")
    public ResponseEntity<AnswerResponse> modifyAnswer(@PathVariable("answerId") Long answerId, @RequestBody AnswerRequest answerRequest){
        AnswerResponse response = answerService.modifyAnswer(answerId, answerRequest);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}