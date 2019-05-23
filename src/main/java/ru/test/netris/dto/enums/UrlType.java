package ru.test.netris.dto.enums;

import lombok.Getter;

public enum UrlType {
    LIVE("LIVE"),
    ARCHIVE("ARCHIVE");

    UrlType(String type){
        this.type = type;
    }

    @Getter
    private String type;
}
