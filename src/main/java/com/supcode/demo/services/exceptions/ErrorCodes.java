package com.supcode.demo.services.exceptions;

public class ErrorCodes {
    //Invalid argument errors
    public static final String PROJECT_NOT_FOUND="101";
    public static final String DEPARTMENT_NOT_FOUND="102";
    public static final String EMPLOYEE_NOT_FOUND="103";
    public static final String PROJECT_NOT_IN_DEPARTMENT="104";
    public static final String EMPLOYEE_NOT_IN_PROJECT="105";
    //Data Access errors
    public static final String DB_ERROR="201";
    //Unknown errors - default
    public static final String UNKNOWN_ERROR="301";
}
