package org.example.items_k8s_service.application.features.create_item;

import org.example.item_categories_interfaces.ItemCategoryDto;
import org.example.items_k8s_service.application.domain.Item;
import org.example.items_k8s_service.application.domain.ItemCategory;
import org.example.items_k8s_service.application.features.shared.ItemDto;
import org.example.items_k8s_service.application.infrastructure.persistence.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional
public class CreateItemServiceImpl implements CreateItemService 
{
    private final ItemRepository itemRepository;

    @Override
    public ItemDto createItem(CreateItemCommand command, ItemCategoryDto categoryDto) 
    {
        var category = ItemCategory.of(categoryDto.getId(), categoryDto.getName());

        var item = Item.of(command.getName(), category);

        item = itemRepository.save(item);

        return ItemDto.of(String.valueOf(item.getId()), item.getName(), categoryDto);
    }
}
