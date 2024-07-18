package org.example.items_service.application.features.create_item;

import org.example.items_service.application.features.shared.FeatureException;

public class CreateItemCommandInCorrectException extends FeatureException 
{
    public CreateItemCommandInCorrectException() 
    {
        super("Create item command isn't correct");
    }
 
    public CreateItemCommandInCorrectException(String message) {
       super(message);
    }
}
