package com.example.codeconnect.post.DTO;

import com.example.codeconnect.entity.Post;
import com.example.codeconnect.post.validation.ValidContactDetails;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ValidContactDetails
public class PostRequest {
    @NotBlank(message = "모집구분을 작성해주세요.")
    private String type;
    @NotBlank(message = "인원모집을 작성해주세요.")
    private String size;
    @NotBlank(message = "진행방식을 작성해주세요.")
    private String processType;
    @NotBlank(message = "진행기간을 작성해주세요.")
    private String period;
    @NotBlank(message = "기술스택을 작성해주세요.")
    private List<String> techStack;
    @NotNull(message = "모집마감일을 작성해주세요.")
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate deadline;
    @NotBlank(message = "모집포지션을 작성해주세요.")
    private List<String> position;
    @NotBlank(message = "연락방법을 작성해주세요.")
    private String contactMethod;
    @NotBlank(message = "링크/메일주소를 작성해주세요.")
    private String contactDetails;
    @NotBlank(message = "제목을 작성해주세요.")
    private String title;
    @NotBlank(message = "본문을 작성해주세요.")
    private String description;


    public Post toEntity() {
        return Post.builder()
                .type(type)
                .size(size)
                .processType(processType)
                .period(period)
                .techStack(techStack)
                .deadline(deadline)
                .position(position)
                .contactMethod(contactMethod)
                .contactDetails(contactDetails)
                .title(title)
                .description(description)
                .createDate(LocalDateTime.now())
                .build();
    }

}

