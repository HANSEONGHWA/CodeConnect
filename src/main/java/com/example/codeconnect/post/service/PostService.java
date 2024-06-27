package com.example.codeconnect.post.service;

import com.example.codeconnect.post.DTO.PostRequest;
import com.example.codeconnect.post.DTO.PostResponse;

public interface PostService {
    void postCreate(PostRequest postRequest);

    PostResponse findById(Long id);
}
