package com.example.codeconnect.answer.controller.DTO;

import com.example.codeconnect.entity.Answer;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AnswerResponse {
    private Long id;
    private String comment;

    public static AnswerResponse fromEntity(Answer answer){
        return AnswerResponse.builder()
                .id(answer.getId())
                .comment(answer.getComment())
                .build();
    }
}
