package ru.test.netris.dto;

import lombok.Data;
import ru.test.netris.dto.enums.UrlType;

@Data
public class SourceCamDto {
    private UrlType urlType;
    private String videoUrl;
}
