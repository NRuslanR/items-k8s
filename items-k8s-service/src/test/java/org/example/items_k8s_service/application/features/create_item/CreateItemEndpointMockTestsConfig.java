package org.example.items_k8s_service.application.features.create_item;

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
