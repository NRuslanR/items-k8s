package org.example.items_service.application.shared.exceptions.handling;

import org.example.items_service.application.features.shared.FeatureException;
import org.example.items_service.application.infrastructure.integration.shared.EndpointIntegrationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import feign.RetryableException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler({
        CallNotPermittedException.class,
        EndpointIntegrationException.class,
        RetryableException.class
    })
    @ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE)
    public ApiError handleEndpointIntegrationExceptions(Exception exception)
    {
        return ApiError.of("Internal services aren't available now");
    }

    @ExceptionHandler(FeatureException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleFeatureException(FeatureException exception)
    {
        return ApiError.of(exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ApiError handleConstraintViolationException(ConstraintViolationException exception)
    {
        return ApiError.of(exception.getMessage());
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) 
    {
        return
            ResponseEntity.badRequest().body(
                ApiError.of(
                    ex.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList()
                    )
            );
    }
}
