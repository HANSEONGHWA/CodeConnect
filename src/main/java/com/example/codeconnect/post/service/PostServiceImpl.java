package com.example.codeconnect.post.service;

import com.example.codeconnect.entity.Post;
import com.example.codeconnect.exception.DataNotFoundException;
import com.example.codeconnect.post.DTO.PostRequest;
import com.example.codeconnect.post.DTO.PostResponse;
import com.example.codeconnect.post.DTO.PostResponseList;
import com.example.codeconnect.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
     *
     * @param id 조회할 Post id
     * @return PostResponse 객체
     * @throws DataNotFoundException 요청한 데이터가 없는 경우 발생
     */
    @Override
    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new DataNotFoundException("요청한 데이터를 찾을 수 없습니다."));
        return PostResponse.fromEntity(post);
    }

    /**
     * post List 조회 및 페이징, 검색 조회
     * @param page 조회 페이지 번호
     * @param type
     * @param techStack
     * @param position
     * @return Page<PostResponseList> 객체
     * @throws DataNotFoundException 조회 데이터가 없는 경우 발생
     */
    @Override
    public Page<PostResponseList> findPostSearch(int page, String type, List<String> techStack, List<String> position) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<PostResponseList> posts  = postRepository.searchPosts(type, techStack, position, pageRequest);
       if (posts.isEmpty()) {
           throw new DataNotFoundException("조회 데이터가 없습니다.");
       }
        return posts;
    }

    @Override
    public Page<PostResponseList> findAll(int page) {
        log.info("page={}", page);
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createDate"));
        Page<Post> postList = postRepository.findAll(pageRequest);
        if (postList.isEmpty()) {
            throw new DataNotFoundException("페이지를 다시 입력해주세요.");
        }
        return postList.map(PostResponseList::fromEntity);
    }
}