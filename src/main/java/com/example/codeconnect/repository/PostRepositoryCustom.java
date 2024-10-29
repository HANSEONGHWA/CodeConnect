package com.example.codeconnect.repository;

import com.example.codeconnect.post.DTO.PostResponseList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryCustom {
    Page<PostResponseList> searchPosts(String type, List<String> techStack, List<String> Positions, Pageable pageable);
}
