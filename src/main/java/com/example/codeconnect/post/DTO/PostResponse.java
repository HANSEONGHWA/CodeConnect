package com.example.codeconnect.post.DTO;

import com.example.codeconnect.entity.Post;
import com.example.codeconnect.entity.PostPosition;
import com.example.codeconnect.entity.PostTechStack;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostResponse {
    private Long id;
    private String type;
    private String size;
    private String processType;
    private String period;
    private List<String> techStack;
    private LocalDate deadline;
    private List<String> position;
    private String contactMethod;
    private String contactDetails;
    private String title;
    private String description;

    /**
     * PostEntity를 PostResponse 객체로 변환
     *
     * @param post 변환할 Entity
     * @return PostResponse 객체
     */
    public static PostResponse fromEntity(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .type(post.getType())
                .size(post.getSize())
                .processType(post.getProcessType())
                .period(post.getPeriod())
                .techStack(post.getTechStacks().stream().map(PostTechStack::getTechStacks).collect(Collectors.toList()))
                .deadline(post.getDeadline())
                .position(post.getPositions().stream().map(PostPosition::getPositions).collect(Collectors.toList()))
                .contactMethod(post.getContactMethod())
                .contactDetails(post.getContactDetails())
                .title(post.getTitle())
                .description(post.getDescription())
                .build();
    }
}


