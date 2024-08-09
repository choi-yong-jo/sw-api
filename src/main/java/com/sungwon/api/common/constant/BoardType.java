package com.sungwon.api.common.constant;

import lombok.Getter;

@Getter
public enum BoardType {

    Notice("Notice","01"),
    ReferenceRoom("ReferenceRoom", "02"),
    Consult("Consult","03");

    private final String name;
    private final String value;

    BoardType(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
