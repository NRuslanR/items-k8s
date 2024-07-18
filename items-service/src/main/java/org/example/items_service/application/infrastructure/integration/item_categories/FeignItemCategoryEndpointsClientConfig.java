package org.example.items_service.application.infrastructure.integration.item_categories;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.ErrorDecoder;

@Configuration
public class FeignItemCategoryEndpointsClientConfig 
{
    @Bean
    public ErrorDecoder errorDecoder()
    {
        return new ItemCategoryEndpointsErrorDecoder();
    }
}
