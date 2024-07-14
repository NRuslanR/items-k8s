package org.example.items_k8s_service.application.features.create_item;

import org.example.items_k8s_service.application.infrastructure.integration.item_categories.GetItemCategoryEndpointClient;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CreateItemImpl implements CreateItem 
{
    private final GetItemCategoryEndpointClient getItemCategoryEndpointClient;
    private final CreateItemService createItemService;

    @Override
    public CreateItemResult run(@Valid CreateItemCommand command) 
    {
        var result = 
            getItemCategoryEndpointClient
                .getItemCategoryByIdSafely(command.getCategoryId()); 
                
        var item = createItemService.createItem(command, result.getItemCategory());

        return CreateItemResult.of(item);
    }
}
