package com.sungwon.api.common.constant;

import lombok.Getter;

@Getter
public enum UploadPath {

    Member("member","01"),
    Board("board","02");

    private final String name;
    private final String value;

    UploadPath(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
