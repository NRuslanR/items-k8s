package org.example.items_k8s_service.application.infrastructure.integration.item_categories.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;

import com.github.tomakehurst.wiremock.WireMockServer;

import reactor.core.publisher.Flux;

@TestComponent
public class ItemCategoryServiceInstanceListSupplier implements ServiceInstanceListSupplier 
{
    @Value("${item-categories-service.name}")
    private String itemCategoriesServiceName;

    @Autowired
    private WireMockServer mockServer;

    @Override
    public Flux<List<ServiceInstance>> get() 
    {
        return Flux.just(
            List.of(        
                new DefaultServiceInstance(
                    itemCategoriesServiceName + "1", 
                    itemCategoriesServiceName, 
                    "localhost", 
                    mockServer.port(), 
                    false
                )
            )
        );
    }

    @Override
    public String getServiceId() 
    {
        return itemCategoriesServiceName;
    }
}
