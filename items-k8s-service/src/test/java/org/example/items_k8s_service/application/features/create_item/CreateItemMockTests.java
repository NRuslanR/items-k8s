package org.example.items_k8s_service.application.features.create_item;

import org.example.items_k8s_service.application.infrastructure.integration.item_categories.GetItemCategoryEndpointClient;
import org.example.items_k8s_service.shared.config.MockIntegrationTest;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@MockIntegrationTest
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    WebMvcAutoConfiguration.class
})
@Import(CreateItemMockTestsConfig.class)
@SpringBootTest(classes = {
    GetItemCategoryEndpointClient.class,
    CreateItemImpl.class
})
public class CreateItemMockTests extends CreateItemTests 
{

}
