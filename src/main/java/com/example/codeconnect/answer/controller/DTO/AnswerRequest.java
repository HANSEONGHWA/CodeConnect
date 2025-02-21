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


    public Answer toEntity() {
        return Answer.builder()
                .comment(comment)
                .createDate(LocalDateTime.now())
                .build();
    }

    public Answer toEntityForUpdate(Answer answer) {
        return Answer.builder()
                .id(answer.getId())
                .post(answer.getPost())
                .comment(this.comment)
                .createDate(answer.getCreateDate())
                .modifyDate(LocalDateTime.now())
                .build();
    }
}

