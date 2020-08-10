package com.inoastrum.pharmaquestionservice.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ErrorObject {
    private final List<String> messages;
    private final Class<? extends Exception> errorClass;

    public ErrorObject(List<String> messages, Class<? extends Exception> errorClass) {
        this.messages = messages;
        this.errorClass = errorClass;
    }
}
