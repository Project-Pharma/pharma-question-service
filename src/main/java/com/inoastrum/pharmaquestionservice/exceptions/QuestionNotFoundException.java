package com.inoastrum.pharmaquestionservice.exceptions;

public class QuestionNotFoundException extends RuntimeException {

    @Override
    public String getMessage() {
        return "Question you are looking for can not be found";
    }
}
