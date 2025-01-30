package com.example.codeconnect.answer.controller.DTO;

import com.example.codeconnect.entity.Answer;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerRequest {
    @NotEmpty(message = "내용을 작성해주세요.")
    private String comment;


    public Answer toEntity(){
        Answer answer = Answer.builder()
                .comment(comment)
                .createDate(LocalDateTime.now())
                .build();
        return answer;
    }
}

