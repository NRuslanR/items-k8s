package org.example.items_service.application.features.create_item;

import org.example.item_categories_interfaces.ItemCategoryDto;
import org.example.items_service.application.domain.Item;
import org.example.items_service.application.domain.ItemCategory;
import org.example.items_service.application.features.shared.ItemDto;
import org.example.items_service.application.infrastructure.persistence.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class CreateItemServiceImpl implements CreateItemService 
{
    private final ItemRepository itemRepository;

    @Override
    public ItemDto createItem(@NonNull CreateItemCommand command, @NonNull ItemCategoryDto categoryDto)
        throws NullPointerException, CreateItemCommandInCorrectException 
    {
        var category = ItemCategory.of(categoryDto.getId(), categoryDto.getName());

        Item item;

        try 
        {
            item = Item.of(command.getName(), category);
            
        } catch (Exception e) 
        {
            throw new CreateItemCommandInCorrectException(e.getMessage());
        }

        item = itemRepository.save(item);

        return ItemDto.of(String.valueOf(item.getId()), item.getName(), categoryDto);
    }
}
