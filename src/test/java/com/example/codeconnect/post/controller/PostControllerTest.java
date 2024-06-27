package com.example.codeconnect.post.controller;

import com.example.codeconnect.post.DTO.PostRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("글 등록 성공")
    void postCreate() throws Exception {

        PostRequest request = PostRequest.builder()
                .type("type")
                .size("2")
                .processType("processType")
                .period("period")
                .techStack(List.of("Java", "Spring"))
                .deadline(LocalDate.of(9999, 1, 1))
                .position(List.of("백엔드", "프론트엔드"))
                .contactMethod("이메일")
                .contactDetails("asas@naver.com")
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
                .type("")
                .size("")
                .processType("")
                .period("")
                .techStack(List.of())
                .deadline(null)
                .position(List.of())
                .contactMethod("")
                .contactDetails("")
                .title("")
                .description("")
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
                .type("type")
                .size("2")
                .processType("processType")
                .period("period")
                .techStack(List.of("Java", "Spring"))
                .deadline(LocalDate.of(2024, 1, 1))
                .position(List.of("백엔드", "프론트엔드"))
                .contactMethod("이메일")
                .contactDetails("asas@naver.com")
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
    @DisplayName("id에 해당하는 Post 조회")
    void postFindById() throws Exception {

        Long postId = 1L;
        ResultActions result = mockMvc.perform(get("/api/posts/{id}", postId));
        result.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.type").exists())
                .andExpect(jsonPath("$.size").exists())
                .andExpect(jsonPath("$.techStack").isArray())
                .andExpect(jsonPath("$.position").isArray())
                .andExpect(jsonPath("$.contactMethod").exists())
                .andExpect(jsonPath("$.contactDetails").exists())
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.description").exists());
    }
}