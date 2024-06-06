package com.example.codeconnect.post.controller;

import com.example.codeconnect.post.DTO.PostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("글 등록 성공")
    void postCreate() throws Exception {

        PostRequest request = PostRequest.builder()
                .type("스터디")
                .size("2")
                .processType("오프라인")
                .period("period")
                .techStack("techStack")
                .deadline(LocalDate.of(2024,6, 20))
                .position("position")
                .contactMethod("contactMethod")
                .contactDetails("contactDetails")
                .title("title")
                .description("description")
                .build();


        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))) // objectMapper를 사용해 객체를 JSON으로 변환.
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("정보 미입력으로 인한 글 저장 실패")
    void postCreateBadRequest() throws Exception {

        PostRequest request = PostRequest.builder()
                .type("스터디")
                .size("2")
                .processType("")
                .period("period")
                .techStack("techStack")
                .deadline(LocalDate.of(2024,6, 20))
                .position("position")
                .contactMethod("")
                .contactDetails("contactDetails")
                .title("title")
                .description("description")
                .build();


        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))) // objectMapper를 사용해 객체를 JSON으로 변환.
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("모집 마감일이 현재 날짜와 같거나 지났으면 글 저장 실패")
    void postCreateDeadline() throws Exception {

        PostRequest request = PostRequest.builder()
                .type("스터디")
                .size("2")
                .processType("processType")
                .period("period")
                .techStack("techStack")
                .deadline(LocalDate.of(2024,6, 6))
                .position("position")
                .contactMethod("contactMethod")
                .contactDetails("contactDetails")
                .title("title")
                .description("description")
                .build();


        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))) // objectMapper를 사용해 객체를 JSON으로 변환.
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}