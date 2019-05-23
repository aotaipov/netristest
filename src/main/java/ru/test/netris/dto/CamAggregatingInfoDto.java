package ru.test.netris.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.test.netris.dto.enums.UrlType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CamAggregatingInfoDto {
    private long id;
    private UrlType urlType;
    private String videoUrl;
    private String value;
    private int ttl;
}
