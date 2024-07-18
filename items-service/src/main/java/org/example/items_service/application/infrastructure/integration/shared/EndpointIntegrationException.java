package org.example.items_service.application.infrastructure.integration.shared;

public class EndpointIntegrationException extends RuntimeException 
{
    public EndpointIntegrationException() {
    }
 
    public EndpointIntegrationException(String message) {
       super(message);
    }
}
