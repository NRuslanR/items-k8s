package org.example.items_service.application.infrastructure.integration.item_categories;

import org.example.items_service.application.infrastructure.integration.shared.EndpointIntegrationException;

public class ItemCategoryNotFoundException extends EndpointIntegrationException 
{
    public ItemCategoryNotFoundException() {
        super("Item category not found");
    }
 
    public ItemCategoryNotFoundException(String message) {
       super(message);
    }
}
