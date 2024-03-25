package com.mysite.CodeConnect.question.controller;

import com.mysite.CodeConnect.answer.AnswerForm;
import com.mysite.CodeConnect.entity.Question;
import com.mysite.CodeConnect.entity.SiteUser;
import com.mysite.CodeConnect.question.QuestionForm;
import com.mysite.CodeConnect.question.QuestionService;
import com.mysite.CodeConnect.question.dto.MainResponse;
import com.mysite.CodeConnect.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    /**
     * 메인 페이지 로드 시 .페이징 기능, 조회기능
     * @param model
     * @param page
     * @return
     */
//    @GetMapping("/list")
//    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
//        Page<Question> paging = questionService.getList(page);
//        model.addAttribute("paging", paging);
//        return "question_list";
//    }

    /**
     * 메인 페이지 로드 시 .페이징 기능, 조회기능 -> restApi로 변경
     * @param page
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<Page<MainResponse>> list(@RequestParam(value = "page", defaultValue = "0") int page) {
        Page<MainResponse> list = questionService.getList(page);
       log.info("list={}", list);
        return ResponseEntity.ok().body(list);
    }

    /**
     * 상세페이지  확인
     * @param model
     * @param id
     * @param answerForm
     * @return
     */
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    /**
     * 질문 등록 페이지 이동
     * @param questionForm
     * @return
     */
    @PreAuthorize("isAuthenticated()") // 로그인이 필요한 메서드 의미-> 로그인페이지로 이동
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    /**
     * 질문 등록 -> 글 작성으로 변경
     * @param questionForm
     * @param bindingResult
     * @param principal
     * @return
     */
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/question/list";
    }

    /**
     * 질문 수정 - 수정할 제목과 내용을 questionForm 객체에 담아 템플릿으로 전달.
     */
    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }

    /**
     * 질문 수정 - 수정 후 저장 시
     */

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 Principal principal, @PathVariable("id") Integer id) {
        System.out.println("ZNPTM:::S" + bindingResult);

        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        System.out.println("sssss" + questionForm);
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }

    /**
     * 질문 삭제
     */
    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    //url로 전달받은 id값을 사용하여 Question 데이터를 조회한 후 로그인한 사용하와 질문의 작성자가 동일할 경우 서비스의 delete메서드로 질문 삭제.
    public String questionDelete(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/question/";
    }

    /**
     * 질문 추천
     */
    @GetMapping("/vote/{id}")
    @PreAuthorize("isAuthenticated()")
    public String questionVote(Principal principal, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
}

