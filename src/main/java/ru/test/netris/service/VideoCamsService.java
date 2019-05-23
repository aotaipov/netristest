package ru.test.netris.service;

import ru.test.netris.dto.ResponseDto;
import ru.test.netris.exception.ApiIntegrationException;

public interface VideoCamsService {
    ResponseDto collectVideoCamInfo() throws ApiIntegrationException;
}
