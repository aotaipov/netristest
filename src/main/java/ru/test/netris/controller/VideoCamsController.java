package ru.test.netris.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.test.netris.dto.ResponseDto;
import ru.test.netris.exception.ApiIntegrationException;
import ru.test.netris.service.VideoCamsService;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VideoCamsController {

    private final VideoCamsService videoCamsService;

    @GetMapping("/getCams")
    public ResponseEntity<ResponseDto> getCamInfo() throws ApiIntegrationException {
        return ResponseEntity.ok(videoCamsService.collectVideoCamInfo());
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDto> handleApiException(ApiIntegrationException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ResponseDto(e.getMessage()));
    }
}
