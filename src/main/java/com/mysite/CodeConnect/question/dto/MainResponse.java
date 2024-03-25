package com.mysite.CodeConnect.question.dto;

import com.mysite.CodeConnect.entity.Answer;
import com.mysite.CodeConnect.entity.SiteUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class MainResponse {

    private String subject;

    private String content;

    private LocalDateTime createDate;

    private List<Answer> answerList;

    private SiteUser author;

    Set<SiteUser> voter;
}
