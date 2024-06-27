package com.example.codeconnect.post.DTO;

import com.example.codeconnect.entity.Post;
import com.example.codeconnect.entity.PostPosition;
import com.example.codeconnect.entity.PostTechStack;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostResponseList {
    private Long id;
    private String type;
    private String processType;
    private List<String> techStack;
    private List<String> position;
    private String title;

    /**
     * PostEntity를 PostResponseList 객체로 변환
     * @param post 변환할 Entity
     * @return PostResponseList 객체
     */
    public static PostResponseList fromEntity(Post post){
        return PostResponseList.builder()
                .id(post.getId())
                .type(post.getType())
                .processType(post.getProcessType())
                .techStack(post.getTechStacks().stream().map(PostTechStack::getTechStacks).collect(Collectors.toList()))
                .position(post.getPositions().stream().map(PostPosition::getPositions).collect(Collectors.toList()))
                .title(post.getTitle())
                .build();
    }
}
