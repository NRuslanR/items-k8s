package org.example.items_service.application.infrastructure.integration.item_categories;

import org.example.item_categories_interfaces.GetItemCategoryEndpoint;
import org.example.item_categories_interfaces.GetItemCategoryResult;
import org.springframework.cloud.openfeign.FeignClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@FeignClient(
    name = "${item-categories-service.name}", 
    path = "${item-categories-service.path}",
    configuration = FeignItemCategoryEndpointsClientConfig.class
)
public interface GetItemCategoryEndpointClient extends GetItemCategoryEndpoint 
{
    @CircuitBreaker(name = "item-categories-cb")
    default GetItemCategoryResult getItemCategoryByIdSafely(String id)
    {
        return getItemCategoryById(id);
    }
}
