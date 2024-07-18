package org.example.items_service.application.features.create_item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.example.item_categories_interfaces.ItemCategoryDto;
import org.javatuples.Pair;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CreateItemServiceTestsConfig 
{
    @Bean("correctInputs")
    public List<Pair<CreateItemCommand, ItemCategoryDto>> correctInputs()
    {
        var category = ItemCategoryDto.of(UUID.randomUUID().toString(), "#1");

        var command = CreateItemCommand.of("#1", category.getId());

        return new ArrayList<>(List.of(Pair.with(command, category)));
    }

    public void setCorrectInputs(List<Pair<CreateItemCommand, ItemCategoryDto>> value) 
    {
        var source = correctInputs();

        source.clear();
        source.addAll(value);
    }
}
