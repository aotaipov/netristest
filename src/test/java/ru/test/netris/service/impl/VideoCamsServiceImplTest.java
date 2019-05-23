package ru.test.netris.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.test.netris.dto.*;
import ru.test.netris.exception.ApiIntegrationException;
import ru.test.netris.service.VideoCamsService;
import ru.test.netris.service.integration.CamSourceIntegration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {VideoCamsServiceImpl.class})
public class VideoCamsServiceImplTest {

    @Autowired
    private VideoCamsService videoCamsService;

    @MockBean
    private CamSourceIntegration camSourceIntegration;

    @Autowired
    private ResourceLoader resourceLoader;

    private ObjectMapper mapper = new ObjectMapper();

    @Test(expected = ApiIntegrationException.class)
    public void shouldThrownExceptionWhenSourceCamDataFetchFailed() throws ApiIntegrationException, IOException {
        //given
        List<AvailableVideoCamDto> availableVideoCamDtos = loadResource("mocky-io-main.json", new TypeReference<List<AvailableVideoCamDto>>() {
        });
        //when
        doReturn(availableVideoCamDtos).when(camSourceIntegration).getAvailableCams();
        doThrow(ApiIntegrationException.class).when(camSourceIntegration).getSourceCamData(anyString());
        //then
        videoCamsService.collectVideoCamInfo();
    }

    @Test
    public void shouldReturnRightAnswer() throws ApiIntegrationException, IOException {
        //given
        List<AvailableVideoCamDto> availableVideoCamDtos = loadResource("mocky-io-main.json", new TypeReference<List<AvailableVideoCamDto>>() {
        });
        SourceCamDto sourceCamDto = loadResource("mocky-source-cam-data.json", new TypeReference<SourceCamDto>() {
        });
        TokenCamDto tokenCamDto = loadResource("mocky-token-cam-data.json", new TypeReference<TokenCamDto>() {
        });
        List<CamAggregatingInfoDto> resultList = loadResource("mocky-right-answer.json", new TypeReference<List<CamAggregatingInfoDto>>() {
        });
        //when
        doReturn(availableVideoCamDtos).when(camSourceIntegration).getAvailableCams();
        doReturn(CompletableFuture.completedFuture(sourceCamDto)).when(camSourceIntegration).getSourceCamData(anyString());
        doReturn(CompletableFuture.completedFuture(tokenCamDto)).when(camSourceIntegration).getTokenCamData(anyString());
        //then
        ResponseDto responseDto = videoCamsService.collectVideoCamInfo();
        Assert.assertEquals(resultList, responseDto.getResultList());
    }

    private String loadResource(String resourceName) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:" + resourceName);
        StringBuilder stringBuilder = new StringBuilder();
        Files.lines(Paths.get(resource.getURI())).forEach(stringBuilder::append);
        return stringBuilder.toString();
    }

    private <T> T loadResource(String resourceName, TypeReference<T> clazz) throws IOException {
        String content = loadResource(resourceName);
        return mapper.readValue(content, clazz);
    }


}