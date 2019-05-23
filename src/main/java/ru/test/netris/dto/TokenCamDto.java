package ru.test.netris.dto;

import lombok.Data;

@Data
public class TokenCamDto {
    private String value;
    private int ttl;
}
