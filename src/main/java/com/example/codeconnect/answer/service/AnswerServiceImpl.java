package com.example.codeconnect.answer.service;

import com.example.codeconnect.answer.controller.DTO.AnswerRequest;
import com.example.codeconnect.answer.controller.DTO.AnswerResponse;
import com.example.codeconnect.entity.Answer;
import com.example.codeconnect.entity.Post;
import com.example.codeconnect.exception.DataNotFoundException;
import com.example.codeconnect.repository.AnswerRepository;
import com.example.codeconnect.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AnswerServiceImpl implements AnswerService {

    private final AnswerRepository answerRepository;
    private final PostRepository postRepository;

    /**
     * 댓글 등록
     * postId가 존재하는지 확인 후 존재하지 않는 경우 DataNotFoundException 발생.
     * answerRequest객체를 toEntity()를 통해 Entity로 변환 후 Answer Entity에 postId set.
     * @param postId 게시글 Id
     * @param answerRequest 사용자가 등록한 댓글
     * @return 저장된 answer entity를 AnswerResponse 객체로 반환
     */
    @Override
    public AnswerResponse createAnswer(Long postId, AnswerRequest answerRequest) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("postId가 존재하지 않습니다."));

        Answer answer = answerRequest.toEntity();
        answer.setPost(post);
        Answer saveAnswer = answerRepository.save(answer);

        return AnswerResponse.fromEntity(saveAnswer);
    }

    /**
     * 댓글 수정
     * answerId가 존재하는지 확인 후 존재하지 않는 경우 DataNotFoundException 발생.
     * answerRequest객체를 toEntityForUpdate()를 통해 Entity로 변환 후 저장.
     * @param answerId 댓글 Id
     * @param answerRequest 사용자가 수정한 댓글
     * @return
     */
    @Override
    public AnswerResponse modifyAnswer(Long answerId, AnswerRequest answerRequest) {
        Answer answer = answerRepository.findById(answerId).orElseThrow(() -> new DataNotFoundException("해당 댓글이 존재하지 않습니다."));
        Answer updateAnswer = answerRequest.toEntityForUpdate(answer);
        Answer saveAnswer = answerRepository.save(updateAnswer);
        return AnswerResponse.fromEntity(saveAnswer);
    }
}
