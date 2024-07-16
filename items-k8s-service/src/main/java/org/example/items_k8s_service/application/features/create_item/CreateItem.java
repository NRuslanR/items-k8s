package org.example.items_k8s_service.application.features.create_item;

import org.example.items_k8s_service.application.infrastructure.integration.item_categories.ItemCategoryNotFoundException;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;

@Validated
public interface CreateItem 
{
    CreateItemResult run(@Valid CreateItemCommand command)
        throws NullPointerException, CreateItemCommandInCorrectException, ItemCategoryNotFoundException;
}
