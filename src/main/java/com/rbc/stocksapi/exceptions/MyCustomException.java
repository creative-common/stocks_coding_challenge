package com.rbc.stocksapi.exceptions;

public class MyCustomException extends RuntimeException {
    public MyCustomException(Throwable throwable) {
        super(throwable.getMessage());
    }
}
