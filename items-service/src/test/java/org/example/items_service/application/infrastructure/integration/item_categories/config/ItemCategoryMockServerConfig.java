package org.example.items_service.application.infrastructure.integration.item_categories.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
    
@TestConfiguration
public class ItemCategoryMockServerConfig 
{
    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer itemCategoryMockServer()
    {
        return new WireMockServer(WireMockConfiguration.options().dynamicHttpsPort());
    }
}
