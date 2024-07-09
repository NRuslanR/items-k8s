package org.example.items_k8s_service.application.shared.exceptions.handling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler(CallNotPermittedException.class)
    @ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
    public ApiError handleCallNotPermittedException(CallNotPermittedException exception)
    {
        return ApiError.of("Internal services aren't available now");
    }
}
