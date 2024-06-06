package com.example.codeconnect.post.controller;

import com.example.codeconnect.post.DTO.PostRequest;
import com.example.codeconnect.post.service.PostServiceImpl;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j

public class PostController {

    private final PostServiceImpl postService;

    /**
     * 게시글 등록
     * @param postRequest @Valid를 이용한 유효성 검사
     * @param bindingResult BindingResult를 이용한 에러 처리
     * @return 검증 및 전송 결과
     */
    @PostMapping("/api/posts")
    public ResponseEntity<String> postCreate(@Valid @RequestBody PostRequest postRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMassages = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining());
            log.info("error={}", errorMassages);
            log.info("bindingResult={}",bindingResult);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("에러메세지: " + errorMassages);
        }
        LocalDate today = LocalDate.now();
        if (postRequest.getDeadline().isBefore(today) || postRequest.getDeadline().isEqual(today)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("모집 마감일을 확인해주세요.");
        }
        postService.postCreate(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("성공적으로 저장했습니다.");
    }
}
