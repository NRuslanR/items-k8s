package org.example.items_service.application.features.create_item;

import org.example.items_service.application.features.create_item.CreateItemImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

@TestConfiguration
@Import({
    CreateItemMockTestsConfig.class,
    CreateItemImpl.class
})
public class CreateItemEndpointMockTestsConfig 
{
    
}
