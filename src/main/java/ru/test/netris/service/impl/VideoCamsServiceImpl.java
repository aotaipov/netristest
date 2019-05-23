package ru.test.netris.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.test.netris.dto.AvailableVideoCamDto;
import ru.test.netris.dto.CamAggregatingInfoDto;
import ru.test.netris.dto.ResponseDto;
import ru.test.netris.dto.SourceCamDto;
import ru.test.netris.exception.ApiIntegrationException;
import ru.test.netris.service.VideoCamsService;
import ru.test.netris.service.integration.CamSourceIntegration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoCamsServiceImpl implements VideoCamsService {

    private final CamSourceIntegration camSourceIntegration;

    @Override
    public ResponseDto collectVideoCamInfo() throws ApiIntegrationException {
        List<AvailableVideoCamDto> availableCams = camSourceIntegration.getAvailableCams();
        List<CompletableFuture<CamAggregatingInfoDto>> completableFutures = new ArrayList<>();
        for (AvailableVideoCamDto availableCam : availableCams) {
            completableFutures.add(aggregateCamInfo(availableCam));
        }
        List<CamAggregatingInfoDto> result = completableFutures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
        return new ResponseDto(result);
    }


    private CompletableFuture<CamAggregatingInfoDto> aggregateCamInfo(AvailableVideoCamDto availableVideoCamDto) throws ApiIntegrationException {
        CompletableFuture<SourceCamDto> sourceCamData = camSourceIntegration.getSourceCamData(availableVideoCamDto.getSourceDataUrl());
        return sourceCamData.thenCombine(camSourceIntegration.getTokenCamData(availableVideoCamDto.getTokenDataUrl()),
                (a, b) -> new CamAggregatingInfoDto(availableVideoCamDto.getId(), a.getUrlType(), a.getVideoUrl(), b.getValue(), b.getTtl()));
    }

}
