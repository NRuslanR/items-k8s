package org.example.items_k8s_service.application.features.create_item;

import org.example.items_k8s_service.application.infrastructure.integration.item_categories.GetItemCategoryEndpointClient;
import org.example.items_k8s_service.application.infrastructure.integration.item_categories.config.GetItemCategoryEndpointClientMockTestsConfig;
import org.example.items_k8s_service.shared.config.MockIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

@MockIntegrationTest
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    WebMvcAutoConfiguration.class
})
@Import(CreateItemMockTestsConfig.class)
@ContextConfiguration(classes = {
    GetItemCategoryEndpointClient.class,
    CreateItemServiceImpl.class,
    CreateItemImpl.class
})
public class CreateItemMockTests extends CreateItemTests 
{
    @Autowired
    private GetItemCategoryEndpointClientMockTestsConfig config;
    
    @BeforeEach
    public void setupForEach()
    {
        config.getCircuitBreaker().transitionToClosedState();
    }
}
