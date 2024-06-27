package com.example.codeconnect.post.service;

import com.example.codeconnect.entity.*;
import com.example.codeconnect.exception.DataNotFoundException;
import com.example.codeconnect.post.DTO.PostRequest;
import com.example.codeconnect.post.DTO.PostResponse;
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
    @Override
    public void postCreate(PostRequest postRequest) {
        postRepository.save(postRequest.toEntity());
    }

    /**
     * id에 해당하는 Post 조회
     * @param id 조회할 Post id
     * @return PostResponse 객체
     * @throws DataNotFoundException 요청한 데이터가 없는 경우 발생
     */
    @Override
    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new DataNotFoundException("요청한 데이터를 찾을 수 없습니다."));
        return PostResponse.fromEntity(post);
    }
}

