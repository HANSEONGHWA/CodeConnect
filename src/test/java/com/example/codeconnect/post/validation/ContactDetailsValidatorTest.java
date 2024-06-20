package com.example.codeconnect.post.validation;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.codeconnect.post.DTO.PostRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

class ContactDetailsValidatorTest {

    private static Validator validator; // 유효성 검사 객체 생성

    @Test
    @DisplayName("올바른 카카오톡 url인 경우")
    public void testValidPostRequestKakao() {
        PostRequest request = PostRequest.builder()
                .type("type")
                .size("size")
                .processType("processTyp")
                .period("period")
                .techStack(List.of("Java", "Spring"))
                .deadline(LocalDate.of(9999, 1, 1))
                .position(List.of("백엔드", "프론트엔드"))
                .contactMethod("카카오톡")
                .contactDetails("https://open.kakao.com/o/someLink")
                .title("title")
                .description("description")
                .build();

        //request 객체의 유효성 검사.
        //ConstraintViolation : 유효성 검사에 실패한 필드의 객체 집합을 반환
        Set<ConstraintViolation<PostRequest>> validate = validator.validate(request);
        assertThat(validate).isEmpty();
    }

    @Test
    @DisplayName("올바른 이메일인 경우")
    public void testValidPostRequestEmail() {
        PostRequest request = PostRequest.builder()
                .type("type")
                .size("size")
                .processType("processTyp")
                .period("period")
                .techStack(List.of("Java", "Spring"))
                .deadline(LocalDate.of(9999, 1, 1))
                .position(List.of("백엔드", "프론트엔드"))
                .contactMethod("이메일")
                .contactDetails("qwdf@naver.com")
                .title("title")
                .description("description")
                .build();

        Set<ConstraintViolation<PostRequest>> validate = validator.validate(request);
        assertThat(validate).isEmpty();
    }

    @Test
    @DisplayName("올바르지 않은 카카오톡 url인 경우")
    public void testInvalidContactDetailsForKakao() {
        PostRequest request = PostRequest.builder()
                .type("type")
                .size("size")
                .processType("processTyp")
                .period("period")
                .techStack(List.of("Java", "Spring"))
                .deadline(LocalDate.of(9999, 1, 1))
                .position(List.of("백엔드", "프론트엔드"))
                .contactMethod("카카오톡")
                .contactDetails("qwdf@naver.com")
                .title("title")
                .description("description")
                .build();

        Set<ConstraintViolation<PostRequest>> validate = validator.validate(request);
        assertThat(validate).isNotEmpty();
    }

    @Test
    @DisplayName("올바르지 않은 이메일인 경우")
    public void testInvalidContactDetailsForEmail() {
        PostRequest request = PostRequest.builder()
                .type("type")
                .size("size")
                .processType("processTyp")
                .period("period")
                .techStack(List.of("Java", "Spring"))
                .deadline(LocalDate.of(9999, 1, 1))
                .position(List.of("백엔드", "프론트엔드"))
                .contactMethod("이메일")
                .contactDetails("qwdf@")
                .title("title")
                .description("description")
                .build();

        Set<ConstraintViolation<PostRequest>> validate = validator.validate(request);
        assertThat(validate).isNotEmpty();
    }
}