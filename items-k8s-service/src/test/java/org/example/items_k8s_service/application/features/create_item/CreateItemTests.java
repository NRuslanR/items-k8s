package org.example.items_k8s_service.application.features.create_item;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CreateItemTests 
{
    @Autowired
    private CreateItemTestsConfig config;
    
    @Autowired
    private CreateItem createItem;

    @ParameterizedTest
    @MethodSource("correctCreateItemCommands")
    public void should_CreateItem_When_CommandIsValid(CreateItemCommand command)
    {
        var result = createItem.run(command);

        assertThat(result, is(notNullValue()));

        var item = result.getItem();

        assertThat(item, is(notNullValue()));
        assertThat(item.getId(), is(not(emptyOrNullString())));
        assertThat(item.getName(), is(command.getName()));

        var category = item.getCategory();

        assertThat(category, is(notNullValue()));

        assertThat(category.getId(), is(command.getCategoryId()));
        assertThat(category.getName(), is(not(emptyOrNullString())));
    }    

    private Stream<Arguments> correctCreateItemCommands()
    {
        return config.correctCreateItemCommands().stream().map(Arguments::of);
    }
}
