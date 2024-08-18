package com.example.EmailSender.enumeration;

public enum StatusEnum {

    SUCCESSFUL(1),
    FAILED(0);

    private Integer code;

    StatusEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}