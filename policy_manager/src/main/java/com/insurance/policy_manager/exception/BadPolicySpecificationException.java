package com.insurance.policy_manager.exception;

public class BadPolicySpecificationException extends RuntimeException{

    public BadPolicySpecificationException(final String message){
        super(message);
    }
}
