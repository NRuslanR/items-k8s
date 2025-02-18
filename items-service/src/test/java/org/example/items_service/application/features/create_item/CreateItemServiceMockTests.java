package org.example.items_service.application.features.create_item;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.example.item_categories_interfaces.ItemCategoryDto;
import org.example.items_service.application.features.shared.ItemDto;
import org.example.items_service.application.infrastructure.persistence.ItemRepository;
import org.example.items_service.shared.config.MockIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@MockIntegrationTest
@Import(CreateItemServiceMockTestsConfig.class)
@SpringBootTest(classes = CreateItemServiceImpl.class)
public class CreateItemServiceMockTests extends CreateItemServiceTests 
{
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CreateItemServiceMockTestsConfig config;

    @Override
    protected void assertItem(ItemDto item, CreateItemCommand command, ItemCategoryDto category) 
    {
        super.assertItem(item, command, category);

        verify(itemRepository, times(config.correctInputs().size())).save(any());
    }
}
