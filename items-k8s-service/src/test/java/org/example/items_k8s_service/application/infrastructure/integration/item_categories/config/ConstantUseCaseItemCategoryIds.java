package org.example.items_k8s_service.application.infrastructure.integration.item_categories.config;

import org.springframework.boot.test.context.TestComponent;

@TestComponent
public class ConstantUseCaseItemCategoryIds implements UseCaseItemCategoryIds
{

    @Override
    public String validItemCategoryId() 
    {
        return "1";
    }

    @Override
    public String serviceUnavailableItemCategoryId() 
    {
        return "2";
    }

    @Override
    public String serviceTimeoutItemCategoryId() 
    {
        return "3";
    }

    @Override
    public String notFoundItemCategoryId() 
    {
        return "4";
    }
}
