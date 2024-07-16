package org.example.items_k8s_service.application.features.create_item;

import java.util.List;

import org.example.items_k8s_service.application.infrastructure.integration.item_categories.config.GetItemCategoryEndpointClientMockTestsConfig;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import jakarta.annotation.PostConstruct;
import lombok.experimental.Delegate;
import wiremock.org.apache.commons.lang3.RandomStringUtils;

@TestConfiguration
@Import({
    GetItemCategoryEndpointClientMockTestsConfig.class,
    CreateItemServiceMockTestsConfig.class,
    CreateItemTestsConfig.class,
    CreateItemServiceImpl.class
})
public class CreateItemMockTestsConfig 
{
    @Autowired
    private GetItemCategoryEndpointClientMockTestsConfig getItemCategoryEndpointClientMockTestsConfig;

    @Autowired
    private CreateItemServiceMockTestsConfig createItemServiceMockTestsConfig;

    @Delegate
    @Autowired
    private CreateItemTestsConfig createItemTestsConfig;

    @PostConstruct
    public void init()
    {
        setCorrectCreateItemCommands();
    }

    private void setCorrectCreateItemCommands() 
    {
        var validItemCategory = 
            getItemCategoryEndpointClientMockTestsConfig.validItemCategoryInstance();

        var createItemCommand = 
            CreateItemCommand.of(RandomStringUtils.random(10), validItemCategory.getId());

        createItemServiceMockTestsConfig.setCorrectInputs(
            List.of(Pair.with(createItemCommand, validItemCategory))
        );

        createItemTestsConfig.setCorrectCreateItemCommands(
            List.of(createItemCommand)
        );
    }
}
