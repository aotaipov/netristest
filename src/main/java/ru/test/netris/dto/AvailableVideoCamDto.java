package ru.test.netris.dto;

import lombok.Data;

@Data
public class AvailableVideoCamDto {
    private int id;
    private String sourceDataUrl;
    private String tokenDataUrl;
}
