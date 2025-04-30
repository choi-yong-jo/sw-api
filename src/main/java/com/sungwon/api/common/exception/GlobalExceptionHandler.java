package com.sungwon.api.common.exception;

import com.sungwon.api.common.dto.ResponseDTO;
import com.sungwon.api.member.exception.MemberServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MemberServiceException.class)
    public ResponseEntity<ResponseDTO> handleCustomServiceException(MemberServiceException ex) {
        ResponseDTO response = ex.getResponseDTO();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 예외에 따라 상태 코드 조정 가능
    }

    // 필요시 다른 예외들도 추가
    // 예: @ExceptionHandler(MethodArgumentNotValidException.class)
}
