package org.example.item_categories_service.application.features.shared.exceptions;

import java.net.http.HttpTimeoutException;

import org.example.item_categories.application.features.get_item_category.FailureException;
import org.example.item_categories.application.features.get_item_category.ItemCategoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class FeatureApiExceptionHandler extends ResponseEntityExceptionHandler 
{
    @ExceptionHandler(ItemCategoryNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ApiError handleItemCategoryNotFoundException(ItemCategoryNotFoundException exception)
    {
        return ApiError.of(exception.getMessage());
    }

    @ExceptionHandler(FailureException.class)
    public ResponseEntity<ApiError> handleFailureException(FailureException exception)
    {
        var cause = exception.getCause();
        
        return 
            ResponseEntity
                .status(
                    cause instanceof HttpServerErrorException ? HttpStatus.SERVICE_UNAVAILABLE : 
                    cause instanceof HttpTimeoutException ? HttpStatus.REQUEST_TIMEOUT : 
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
                .body(ApiError.of(cause.getMessage()));
        
    }
}
