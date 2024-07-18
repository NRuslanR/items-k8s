package org.example.items_service.application.features.create_item;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.stream.Stream;

import org.example.item_categories_interfaces.ItemCategoryDto;
import org.example.items_service.application.features.shared.ItemDto;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CreateItemServiceTests 
{
    @Autowired
    private CreateItemService createItemService;

    @Autowired
    private CreateItemServiceTestsConfig config;
    
    @ParameterizedTest
    @MethodSource("correctInputs")
    public void should_CreateItem_When_InputIsCorrect(CreateItemCommand command, ItemCategoryDto category)
    {
        var item = createItemService.createItem(command, category);

        assertItem(item, command, category);
    }

    protected void assertItem(ItemDto item, CreateItemCommand command, ItemCategoryDto category) 
    {
        assertThat(item, is(notNullValue()));
        assertThat(item.getId(), is(not(emptyOrNullString())));   
        assertThat(item.getName(), is(command.getName()));
        assertThat(item.getCategory().getId(), is(category.getId()));
        assertThat(item.getCategory().getName(), is(category.getName()));
    }

    private Stream<Arguments> correctInputs()
    {
        return config.correctInputs().stream().map(p -> Arguments.of(p.getValue0(), p.getValue1()));
    }    
}
