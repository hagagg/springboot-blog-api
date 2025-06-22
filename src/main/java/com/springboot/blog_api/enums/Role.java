package com.springboot.blog_api.enums;


import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    USER,
    ADMIN;

    @JsonCreator
    public static Role fromString(String value) {
        return value == null ? null : Role.valueOf(value.toUpperCase());
    }
}
