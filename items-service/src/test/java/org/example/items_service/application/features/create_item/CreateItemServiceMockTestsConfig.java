package org.example.items_service.application.features.create_item;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.example.item_categories_interfaces.ItemCategoryDto;
import org.example.items_service.application.domain.Item;
import org.example.items_service.application.features.create_item.CreateItemCommand;
import org.example.items_service.application.infrastructure.persistence.ItemRepository;
import org.javatuples.Pair;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import lombok.experimental.Delegate;

@TestConfiguration
@Import({
    CreateItemServiceTestsConfig.class
})
public class CreateItemServiceMockTestsConfig
{
    @Autowired
    @Delegate
    private CreateItemServiceTestsConfig createItemServiceTestsConfig;

    @Bean
    public ItemRepository itemRepository()
    {
        var itemRepository = Mockito.mock(ItemRepository.class);

        when(itemRepository.save(any())).thenAnswer(i -> {
            
            var item = (Item)i.getArgument(0);

            item.setId(1);

            return item;
        });

        return itemRepository;
    }

    public void setCorrectInputs(List<Pair<CreateItemCommand, ItemCategoryDto>> value) 
    {
        createItemServiceTestsConfig.setCorrectInputs(value);
    }
}
