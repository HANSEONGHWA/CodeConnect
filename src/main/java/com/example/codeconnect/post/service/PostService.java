package com.example.codeconnect.post.service;

import com.example.codeconnect.post.DTO.PostRequest;
import com.example.codeconnect.post.DTO.PostResponse;
import com.example.codeconnect.post.DTO.PostResponseList;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    void postCreate(PostRequest postRequest);

    PostResponse findById(Long id);

    Page<PostResponseList> findAll(int page);

    Page<PostResponseList> findPostSearch(int page, String type, List<String> techStack, List<String> position);
}
