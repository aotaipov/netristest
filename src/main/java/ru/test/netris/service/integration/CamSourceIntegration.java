package ru.test.netris.service.integration;

import ru.test.netris.dto.AvailableVideoCamDto;
import ru.test.netris.dto.SourceCamDto;
import ru.test.netris.dto.TokenCamDto;
import ru.test.netris.exception.ApiIntegrationException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CamSourceIntegration {
    List<AvailableVideoCamDto> getAvailableCams() throws ApiIntegrationException;

    CompletableFuture<SourceCamDto> getSourceCamData(String sourceDataUrl) throws ApiIntegrationException;

    CompletableFuture<TokenCamDto> getTokenCamData(String tokenDataUrl) throws ApiIntegrationException;
}
