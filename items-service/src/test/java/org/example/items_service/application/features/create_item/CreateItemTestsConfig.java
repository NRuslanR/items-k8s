package org.example.items_service.application.features.create_item;

import java.util.ArrayList;
import java.util.List;

import org.example.items_service.application.infrastructure.integration.item_categories.config.GetItemCategoryEndpointClientMockTestsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

import lombok.Data;

@Data
@TestConfiguration
@Import({
    GetItemCategoryEndpointClientMockTestsConfig.class
})
public class CreateItemTestsConfig 
{
    @Autowired
    private GetItemCategoryEndpointClientMockTestsConfig getItemCategoryEndpointClientMockTestsConfig;

    @Bean
    public List<CreateItemCommand> correctCreateItemCommands()
    {
        var validItemCategoryId = 
            getItemCategoryEndpointClientMockTestsConfig
                .validItemCategoryId();

        return new ArrayList<>(
            List.of(
                CreateItemCommand.of(
                    RandomStringUtils.random(10), 
                    validItemCategoryId)
                )
        );
    }

    @Bean
    public List<CreateItemCommand> inCorrectCreateItemCommands()
    {
        return new ArrayList<>(
            List.of(
                CreateItemCommand.of(null, null),
                CreateItemCommand.of(" ", null),
                CreateItemCommand.of(null, " "),
                CreateItemCommand.of(" ", " ")
            )
        );
    }

    public void setCorrectCreateItemCommands(List<CreateItemCommand> value) 
    {
        var source = correctCreateItemCommands();

        source.clear();
        source.addAll(value);
    }

    @Bean
    public List<CreateItemCommand> createItemCommandsForFailedIntegration() 
    {
        var commands = new ArrayList<CreateItemCommand>();

        commands.add(
            CreateItemCommand.of(
                RandomStringUtils.random(10), 
                getItemCategoryEndpointClientMockTestsConfig
                .serviceUnavailableItemCategoryId()
            )
        );

        commands.add(
            CreateItemCommand.of(
                RandomStringUtils.random(10), 
                getItemCategoryEndpointClientMockTestsConfig
                    .notFoundItemCategoryId()
            )
        );

        return commands;
    }

    public List<CreateItemCommand> createItemCommandsForCircuitBreakerOpening() 
    {
        var serviceTimeoutItemCategoryId =
            getItemCategoryEndpointClientMockTestsConfig
                .serviceTimeoutItemCategoryId();

        var createItemCommand =
            CreateItemCommand.of(
                RandomStringUtils.random(10), 
                serviceTimeoutItemCategoryId
            );

        var commands = new ArrayList<CreateItemCommand>();

        commands.add(createItemCommand);

        return commands;
    }
}
