package org.example.items_service.application.features.create_item;

import org.example.item_categories_interfaces.ItemCategoryDto;
import org.example.items_service.application.features.shared.ItemDto;

public interface CreateItemService 
{
    ItemDto createItem(CreateItemCommand command, ItemCategoryDto category)
        throws NullPointerException, CreateItemCommandInCorrectException;    
}
