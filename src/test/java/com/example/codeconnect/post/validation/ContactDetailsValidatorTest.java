package com.example.codeconnect.post.validation;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.codeconnect.post.DTO.PostRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

class ContactDetailsValidatorTest {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("올바른 카카오톡 url인 경우")
    public void testValidPostRequestKakao() {
        PostRequest request = PostRequest.builder()
                .type("type")
                .size("size")
                .processType("processTyp")
                .period("period")
                .techStack("techStack")
                .deadline(LocalDate.of(2024, 6, 20))
                .position("position")
                .contactMethod("카카오톡")
                .contactDetails("https://open.kakao.com/o/someLink")
                .title("title")
                .description("description")
                .build();

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
                .techStack("techStack")
                .deadline(LocalDate.of(2024, 6, 20))
                .position("position")
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
    public void testInvalidContactDetailsForKakao(){
            PostRequest request = PostRequest.builder()
                    .type("type")
                    .size("size")
                    .processType("processTyp")
                    .period("period")
                    .techStack("techStack")
                    .deadline(LocalDate.of(2024, 6, 20))
                    .position("position")
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
    public void testInvalidContactDetailsForEmail(){
        PostRequest request = PostRequest.builder()
                .type("type")
                .size("size")
                .processType("processTyp")
                .period("period")
                .techStack("techStack")
                .deadline(LocalDate.of(2024, 6, 20))
                .position("position")
                .contactMethod("이메일")
                .contactDetails("qwdf@")
                .title("title")
                .description("description")
                .build();

        Set<ConstraintViolation<PostRequest>> validate = validator.validate(request);
        assertThat(validate).isNotEmpty();
    }
}