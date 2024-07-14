package org.example.items_k8s_service.application.features.create_item;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import org.example.item_categories_interfaces.ItemCategoryDto;
import org.example.items_k8s_service.application.features.shared.ItemDto;
import org.example.items_k8s_service.application.infrastructure.persistence.ItemRepository;
import org.example.items_k8s_service.shared.config.MockIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

@MockIntegrationTest
@Import(CreateItemServiceMockTestsConfig.class)
@ContextConfiguration(classes = CreateItemServiceImpl.class)
public class CreateItemServiceMockTests extends CreateItemServiceTests 
{
    @Autowired
    private ItemRepository itemRepository;

    @Override
    protected void assertItem(ItemDto item, CreateItemCommand command, ItemCategoryDto category) 
    {
        super.assertItem(item, command, category);

        verify(itemRepository, atLeastOnce()).save(any());
    }
}
