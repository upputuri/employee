package com.supcode.demo.services.exceptions;

import java.util.Date;

import lombok.Data;

public @Data class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
    private String errorCode;
  
    public ErrorDetails(Date timestamp, String message, String details, String errorCode) {
      super();
      this.timestamp = timestamp;
      this.message = message;
      this.details = details;
      this.errorCode = errorCode;
    }
}
