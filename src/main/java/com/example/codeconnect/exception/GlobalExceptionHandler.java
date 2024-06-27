package com.example.codeconnect.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * DataNotFoundException 예외 처리
     * @param e 발생한  DataNotFoundException 예외 객체
     * @return HttpStatus 404 및 에러메세지
     */
    @ExceptionHandler
    public ResponseEntity<String> handleDataNotFoundException(DataNotFoundException e){
        log.error("Data not found={} ", e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
