package com.example.codeconnect.post.service;

import com.example.codeconnect.post.DTO.PostRequest;
import com.example.codeconnect.post.DTO.PostResponse;
import com.example.codeconnect.post.DTO.PostResponseList;

import java.util.List;

public interface PostService {
    void postCreate(PostRequest postRequest);

    PostResponse findById(Long id);

    List<PostResponseList> findAll();
}
