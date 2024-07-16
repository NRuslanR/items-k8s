package org.example.items_k8s_service.application.infrastructure.integration.item_categories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.items_k8s_service.application.infrastructure.integration.item_categories.config.GetItemCategoryEndpointClientMockTestsConfig;
import org.example.items_k8s_service.shared.config.MockIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;

@MockIntegrationTest
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    WebMvcAutoConfiguration.class
})
@Import(GetItemCategoryEndpointClientMockTestsConfig.class)
@SpringBootTest(classes = GetItemCategoryEndpointClient.class)
public class GetItemCategoryEndpointClientMockTests 
{
    @Autowired
    private GetItemCategoryEndpointClientMockTestsConfig config;
    
    @Autowired
    private GetItemCategoryEndpointClient getItemCategoryEndpointClient;    

    @BeforeEach
    public void setupForEach()
    {
        config.resetFailedStates();
    }
    
    @Test
    public void should_ReturnItemCategoryById_When_ItemCategoryExists()
    {
        var result = getItemCategoryEndpointClient.getItemCategoryByIdSafely(config.validItemCategoryId());

        assertThat(result, is(notNullValue()));

        var itemCategory = result.getItemCategory();

        assertThat(itemCategory, is(notNullValue()));
        assertThat(itemCategory.getId(), is(config.validItemCategoryId()));
        assertThat(itemCategory.getName(), is(not(emptyOrNullString())));
    }

    @Test
    public void should_RaiseError_When_ItemCategoryNotFound()
    {
        assertThrows(ItemCategoryNotFoundException.class, () -> {
            
            getItemCategoryEndpointClient
                .getItemCategoryByIdSafely(config.notFoundItemCategoryId());

        });
    }

    @Test
    public void should_CircuitBreaker_Be_Opened_When_ServiceUnavailable()
    {
        assertThrows(CallNotPermittedException.class, () -> {

            config.executeToOpenCircuitBreaker(() -> {

                getItemCategoryEndpointClient
                    .getItemCategoryByIdSafely(config.serviceUnavailableItemCategoryId());
            });
            
        });
    }

    @Test
    public void should_CircuitBreaker_Be_Open_When_ServiceTimeout()
    {
        assertThrows(CallNotPermittedException.class, () -> {

            config.executeToOpenCircuitBreaker(() -> {

                getItemCategoryEndpointClient
                    .getItemCategoryByIdSafely(config.serviceTimeoutItemCategoryId());
                    
            });

        });
    }
}
