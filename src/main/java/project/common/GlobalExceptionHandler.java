package project.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import project.model.ClientException;
import project.model.CommonResponse;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ClientException.class)
    public Mono<CommonResponse<Integer, String>> handleClientError(ClientException e) {
        return Mono.just(new CommonResponse<>(9000, "Client call error"));
    }

    @ExceptionHandler(Exception.class)
    public Mono<CommonResponse<Integer, String>> handleExceptionError(Exception e) {
        return Mono.just(new CommonResponse<>(1000, "Server error"));
    }
}
