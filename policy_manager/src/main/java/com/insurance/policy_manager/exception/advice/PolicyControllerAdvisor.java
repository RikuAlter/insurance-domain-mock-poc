package com.insurance.policy_manager.exception.advice;

import com.insurance.policy_manager.exception.BadPolicySpecificationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PolicyControllerAdvisor {

    @ExceptionHandler(BadPolicySpecificationException.class)
    public ResponseEntity<String> handleBadPolicySpecificationException(final BadPolicySpecificationException e){
        return ResponseEntity.badRequest()
                .body(e.getMessage());
    }
}
