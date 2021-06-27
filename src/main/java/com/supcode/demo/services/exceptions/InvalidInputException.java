package com.supcode.demo.services.exceptions;

import lombok.Data;

public @Data class InvalidInputException extends Exception{
    private String errorCode;
    public InvalidInputException(String errorCode, String msg, Exception e){
        super(msg, e);
        this.errorCode = errorCode;
    }
}
