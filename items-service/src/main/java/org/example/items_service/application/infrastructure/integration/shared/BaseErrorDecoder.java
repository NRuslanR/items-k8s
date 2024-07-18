package org.example.items_service.application.infrastructure.integration.shared;

import feign.Response;
import feign.codec.ErrorDecoder;

public class BaseErrorDecoder implements ErrorDecoder
{
    @Override
    public Exception decode(String methodKey, Response response) 
    {
        return new EndpointIntegrationException(response.reason());
    }
}
