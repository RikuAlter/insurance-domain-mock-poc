package com.insurance.policy_manager.unit.exception.advice;

import com.insurance.policy_manager.exception.BadPolicySpecificationException;
import com.insurance.policy_manager.exception.advice.PolicyControllerAdvisor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PolicyControllerAdvisorTest {

    PolicyControllerAdvisor policyControllerAdvisor;

    @BeforeEach
    public void setUp(){
        policyControllerAdvisor = new PolicyControllerAdvisor();
    }

    @Test
    void handleBadPolicySpecificationException() {
        ResponseEntity<String> response = policyControllerAdvisor.handleBadPolicySpecificationException(new BadPolicySpecificationException("ERROR"));
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}