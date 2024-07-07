package com.likelion.byuldajul.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //400 Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        // ErrorResponse 객체 생성
        ErrorResponse errorResponse = new ErrorResponse("Bad Request", e.getMessage());
        // ResponseEntity 객체를 생성하여 반환 (응답 본문: errorResponse, HTTP 상태 코드: 400)
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    //404 Not Found
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElementException(NoSuchElementException e) {
        // ErrorResponse 객체 생성
        ErrorResponse errorResponse = new ErrorResponse("Not Found", e.getMessage());
        // ResponseEntity 객체를 생성하여 반환 (응답 본문: errorResponse, HTTP 상태 코드: 404)
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    //409 Conflict
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflictException(ConflictException e) {
        // ErrorResponse 객체 생성
        ErrorResponse errorResponse = new ErrorResponse("Conflict", e.getMessage());
        // ResponseEntity 객체를 생성하여 반환 (응답 본문: errorResponse, HTTP 상태 코드: 409)
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    //컨트롤러 메서드에서 @Valid 어노테이션을 사용하여 DTO의 유효성 검사를 수행
    //유효성 검사에 실패하면 MethodArgumentNotValidException 예외가 발생 -> GlobalExceptionHandler에서 예외처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 유효성 검사 실패한 필드와 메시지를 맵에 저장
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        // ErrorResponse 객체 생성
        ErrorResponse errorResponse = new ErrorResponse("Validation Failed", errors.toString());
        // ResponseEntity 객체를 생성하여 반환 (응답 본문: errorResponse, HTTP 상태 코드: 400)
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
