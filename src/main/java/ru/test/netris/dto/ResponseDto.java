package ru.test.netris.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseDto {
    private List<CamAggregatingInfoDto> resultList;
    private String errorMessage;

    public ResponseDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ResponseDto(List<CamAggregatingInfoDto> resultList) {
        this.resultList = resultList;
    }
}
