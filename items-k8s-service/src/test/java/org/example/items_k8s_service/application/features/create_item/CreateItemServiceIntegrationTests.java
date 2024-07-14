package org.example.items_k8s_service.application.features.create_item;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.example.item_categories_interfaces.ItemCategoryDto;
import org.example.items_k8s_service.application.features.shared.ItemDto;
import org.example.items_k8s_service.application.infrastructure.persistence.ItemRepository;
import org.example.items_k8s_service.shared.config.IntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

@IntegrationTest
@Import(CreateItemServiceTestsConfig.class)
public class CreateItemServiceIntegrationTests extends CreateItemServiceTests
{
    @Autowired
    private ItemRepository itemRepository;

    @Override
    protected void assertItem(ItemDto item, CreateItemCommand command, ItemCategoryDto category) 
    {
        super.assertItem(item, command, category);

        assertTrue(itemRepository.existsById(Long.valueOf(item.getId())));
    }
}
