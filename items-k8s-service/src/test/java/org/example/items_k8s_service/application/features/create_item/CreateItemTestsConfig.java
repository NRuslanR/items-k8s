package org.example.items_k8s_service.application.features.create_item;

import java.util.ArrayList;
import java.util.List;

import org.example.items_k8s_service.application.infrastructure.integration.item_categories.config.GetItemCategoryEndpointClientMockTestsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

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

    public void setCorrectCreateItemCommands(List<CreateItemCommand> value) 
    {
        var source = correctCreateItemCommands();

        source.clear();
        source.addAll(value);
    }
}
