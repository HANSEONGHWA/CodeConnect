package com.example.codeconnect.post.validation;

import com.example.codeconnect.post.DTO.PostRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ContactDetailsValidator implements ConstraintValidator<ValidContactDetails, PostRequest> {

    /**
     * contactMethod에 따른 링크와 이메일 검증
     * @param postRequest object to validate
     * @param context context in which the constraint is evaluated
     * @return 검증 결과
     */
    @Override
    public boolean isValid(PostRequest postRequest, ConstraintValidatorContext context) {
        String contactMethod = postRequest.getContactMethod();
        String contactDetails = postRequest.getContactDetails();

        if (postRequest.getContactMethod().equals("오픈톡")){
            return contactDetails.startsWith("https://");
        } else if (contactMethod.equals("이메일")) {
            return contactDetails.matches("^[\\w\\.-]+@[a-zA-Z\\d\\.-]+\\.[a-zA-Z]{2,}$");
        }
        return false;
    }
}
