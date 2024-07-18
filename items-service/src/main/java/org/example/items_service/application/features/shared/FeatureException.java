package org.example.items_service.application.features.shared;

public class FeatureException extends RuntimeException 
{
    public FeatureException() {
    }
 
    public FeatureException(String message) {
       super(message);
    }
}
