package com.example.codeconnect.post.DTO;

import com.example.codeconnect.entity.*;
import com.example.codeconnect.post.validation.ValidContactDetails;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ValidContactDetails
@Slf4j
public class PostRequest {
    @NotBlank(message = "모집구분을 작성해주세요.")
    private String type;
    @NotBlank(message = "인원모집을 작성해주세요.")
    private String size;
    @NotBlank(message = "진행방식을 작성해주세요.")
    private String processType;
    @NotBlank(message = "진행기간을 작성해주세요.")
    private String period;
    @NotEmpty(message = "기술스택을 작성해주세요.")
    private List<String> techStack;
    @NotNull(message = "모집마감일을 작성해주세요.")
    @JsonFormat(pattern = "yyyyMMdd")
    private LocalDate deadline;
    @NotEmpty(message = "모집포지션을 작성해주세요.")
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

        Post post = Post.builder()
                .type(type)
                .size(size)
                .processType(processType)
                .period(period)
                .deadline(deadline)
                .contactMethod(contactMethod)
                .contactDetails(contactDetails)
                .title(title)
                .description(description)
                .createDate(LocalDateTime.now())
                .build();

        //List<String> 타입의 techStack을 List<PostTechStack> 타입의 리스트로 변환.
        List<PostTechStack> postTechStacks = this.techStack.stream()
                .map(stackName -> PostTechStack.builder().post(post).techStacks(stackName).build())
                .collect(Collectors.toList());

        List<PostPosition> postPositions = this.position.stream()
                .map(positionName -> PostPosition.builder().post(post).positions(positionName).build())
                .collect(Collectors.toList());

        post.setTechStacks(postTechStacks);
        post.setPositions(postPositions);

        return post;
    }

}

