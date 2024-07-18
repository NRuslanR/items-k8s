package org.example.items_service.application.infrastructure.integration.item_categories.config;

public interface UseCaseItemCategoryIds 
{
    String validItemCategoryId();
    String serviceUnavailableItemCategoryId();
    String serviceTimeoutItemCategoryId();
    String notFoundItemCategoryId();    
}
