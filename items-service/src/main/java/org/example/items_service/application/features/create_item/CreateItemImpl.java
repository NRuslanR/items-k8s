package org.example.items_service.application.features.create_item;

import org.example.items_service.application.infrastructure.integration.item_categories.GetItemCategoryEndpointClient;
import org.example.items_service.application.infrastructure.integration.item_categories.ItemCategoryNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Validated
public class CreateItemImpl implements CreateItem 
{
    private final GetItemCategoryEndpointClient getItemCategoryEndpointClient;
    private final CreateItemService createItemService;

    @Override
    public CreateItemResult run(@Valid CreateItemCommand command) 
        throws NullPointerException, CreateItemCommandInCorrectException, ItemCategoryNotFoundException
    {
        var result = 
            getItemCategoryEndpointClient
                .getItemCategoryByIdSafely(command.getCategoryId()); 
                
        var item = createItemService.createItem(command, result.getItemCategory());

        return CreateItemResult.of(item);
    }
}
