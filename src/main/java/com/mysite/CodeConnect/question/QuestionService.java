package com.mysite.CodeConnect.question;

import com.mysite.CodeConnect.DataNotFoundException;
import com.mysite.CodeConnect.entity.Question;
import com.mysite.CodeConnect.entity.SiteUser;
import com.mysite.CodeConnect.question.dto.MainResponse;
import com.mysite.CodeConnect.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;

    /**
     * 메인 페이지 list
     * @return
     */
    public List<Question> getList(){
        return this.questionRepository.findAll();
    }

    /**
     * 상세페이지 list
     * @param id
     * @return
     */
    public Question getQuestion(Integer id){
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()){
            return question.get();
        }else {
            throw new DataNotFoundException("question not found");
        }
    }

    /**
     * 질문 생성
     */
    public void create(String subject, String content, SiteUser user ){
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setAuthor(user);
        question.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    /**
     * 페이징
     */
//    public Page<Question> getList(int page){
//        List<Sort.Order> sorts = new ArrayList<>();
//        sorts.add(Sort.Order.desc("createDate"));
//        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts));
//        return this.questionRepository.findAll(pageable);
//    }
//

    /**
     * main poge list & paging
     * @param page
     * @return
     */
    public Page<MainResponse> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<Question> questionPage = this.questionRepository.findAll(pageable);

        List<MainResponse> mainResponses = questionPage.getContent().stream()
                .map(this::convertToMainResponse)
                .collect(Collectors.toList());
        log.info("mainResponses={}",mainResponses);

        return new PageImpl<>(mainResponses, pageable, questionPage.getTotalElements());
    }

    /**
     * Question -> MainResponseDto에 builder
     * @param question
     * @return
     */
    private MainResponse convertToMainResponse(Question question) {
            return MainResponse.builder()
                    .subject(question.getSubject())
                    .content(question.getContent())
                    .createDate(question.getCreateDate())
                    .answerList(question.getAnswerList())
                    .author(question.getAuthor())
                    .voter(question.getVoter())
                    .build();
        }

    /**
     * 질문 수정
     */
    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    /**
     *질문 삭제
     */
    public void delete(Question question){
        this.questionRepository.delete(question);
    }

    /**
     * 추천인 저장
     */
    public void vote(Question question, SiteUser siteUser){
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
}
