package org.example.items_k8s_service.application.features.create_item;

import org.example.items_k8s_service.application.infrastructure.integration.item_categories.config.GetItemCategoryEndpointClientMockTestsConfig;
import org.example.items_k8s_service.shared.config.IntegrationTest;
import org.springframework.context.annotation.Import;

@IntegrationTest
@Import({
    GetItemCategoryEndpointClientMockTestsConfig.class,
    CreateItemTestsConfig.class
})
public class CreateItemIntegrationTests extends CreateItemTests
{
    
}
