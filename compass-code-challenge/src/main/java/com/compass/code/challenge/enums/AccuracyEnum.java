package com.compass.code.challenge.enums;

import lombok.Getter;

@Getter
public enum AccuracyEnum {
    HIGH("HIGH"),
    MEDIUM("MEDIUM"),
    LOW( "LOW");

    private final String value;

    AccuracyEnum(String value) {
        this.value = value;
    }

}