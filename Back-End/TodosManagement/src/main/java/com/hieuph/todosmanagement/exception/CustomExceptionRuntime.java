package com.hieuph.todosmanagement.exception;

public class CustomExceptionRuntime extends RuntimeException{
    private int code;
    public CustomExceptionRuntime(int code, String message){
        super(message);
        this.code = code;
    }
    public CustomExceptionRuntime(String message, Throwable cause){
        super(message, cause);
    }
    public CustomExceptionRuntime(Throwable cause){
        super(cause);
    }
    protected CustomExceptionRuntime(String message, Throwable cause, boolean enableSuppression, boolean wriableStackTrace){
        super(message, cause, enableSuppression, wriableStackTrace);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
