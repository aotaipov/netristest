package ru.test.netris.service.integration.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.test.netris.dto.AvailableVideoCamDto;
import ru.test.netris.dto.SourceCamDto;
import ru.test.netris.dto.TokenCamDto;
import ru.test.netris.exception.ApiIntegrationException;
import ru.test.netris.service.integration.CamSourceIntegration;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class CamSourceIntegrationImpl implements CamSourceIntegration {

    private final ObjectMapper mapper;

    private final RestTemplate restTemplate;

    @Value("${mock-available-cams-url}")
    private String mockAvailableCamsUrl;

    @Override
    public List<AvailableVideoCamDto> getAvailableCams() throws ApiIntegrationException {
        return proceedRequest(new TypeReference<List<AvailableVideoCamDto>>() {
        }, mockAvailableCamsUrl);
    }

    @Override
    @Async
    public CompletableFuture<SourceCamDto> getSourceCamData(String sourceDataUrl) throws ApiIntegrationException {
        return CompletableFuture.completedFuture(proceedRequest(new TypeReference<SourceCamDto>() {
        }, sourceDataUrl));
    }

    @Override
    @Async
    public CompletableFuture<TokenCamDto> getTokenCamData(String tokenDataUrl) throws ApiIntegrationException {
        return CompletableFuture.completedFuture(proceedRequest(new TypeReference<TokenCamDto>() {
        }, tokenDataUrl));
    }

    private <T> T proceedRequest(TypeReference<T> responseClassTypeReference, String url) throws ApiIntegrationException {
        UriComponentsBuilder builder = UriComponentsBuilder
                .fromHttpUrl(url);
        log.info("Request to {}", builder.toUriString());
        HttpEntity<?> entity = new HttpEntity<>(null);
        T responseDto;
        try {
            String body = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, String.class).getBody();
            log.info("Response {}", body);
            responseDto = mapper.readValue(body, responseClassTypeReference);
        } catch (HttpStatusCodeException e) {
            throw new ApiIntegrationException(e.getResponseBodyAsString());
        } catch (IOException e1) {
            throw new ApiIntegrationException("Can not deserialize response to " + responseClassTypeReference.toString());
        }
        return responseDto;
    }
}
