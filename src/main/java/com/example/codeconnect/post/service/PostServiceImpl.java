package com.example.codeconnect.post.service;

import com.example.codeconnect.post.DTO.PostRequest;
import com.example.codeconnect.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    /**
     * 게시글 등록
     */
    public void postCreate(PostRequest postRequest) {
        postRepository.save(postRequest.toEntity());
    }

}
