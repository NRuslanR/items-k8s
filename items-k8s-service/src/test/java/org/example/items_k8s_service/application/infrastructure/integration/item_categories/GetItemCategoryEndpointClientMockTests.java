package org.example.items_k8s_service.application.infrastructure.integration.item_categories;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.serviceUnavailable;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathTemplate;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.IntStream;

import org.example.item_categories_interfaces.GetItemCategoryResult;
import org.example.item_categories_interfaces.ItemCategoryDto;
import org.example.items_k8s_service.application.infrastructure.integration.shared.EndpointIntegrationException;
import org.example.items_k8s_service.shared.config.MockIntegrationTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.SneakyThrows;

@MockIntegrationTest
@SpringBootTest(
    webEnvironment = WebEnvironment.NONE,
    classes = {
        ItemCategoryMockServerConfig.class,
        ItemCategoryServiceInstanceListSupplier.class
    }
)
@EnableAutoConfiguration(
    exclude = {
        DataSourceAutoConfiguration.class,
        WebMvcAutoConfiguration.class
    }
)
public class GetItemCategoryEndpointClientMockTests 
{
    private static final String VALID_ITEM_CATEGORY_ID = "1";
    private static final String SERVICE_UNAVAILABLE_ITEM_CATEGORY_ID = "2";
    private static final String SERVICE_TIMEOUT_ITEM_CATEGORY_ID = "3";
    private static final String NOT_FOUND_ITEM_CATEGORY_ID = "4";

    @Autowired
    private GetItemCategoryEndpointClient getItemCategoryEndpointClient;    

    @Autowired
    private WireMockServer mockServer;

    @Autowired
    private ObjectMapper objectMapper;

    private CircuitBreaker circuitBreaker;
    private CircuitBreakerConfig circuitBreakerConfig;

    @Value("${item-categories-service.path}")
    private String itemCategoryServicePath;

    @BeforeAll
    public void setup(@Autowired CircuitBreakerRegistry circuitBreakerRegistry)
    {
        circuitBreaker = circuitBreakerRegistry.circuitBreaker("item-categories-cb");
        circuitBreakerConfig = circuitBreaker.getCircuitBreakerConfig();

        mockServer
            .stubFor(
                get(urlPathTemplate(itemCategoryServicePath + "/{id}"))
                    .withPathParam("id", equalTo(VALID_ITEM_CATEGORY_ID))
                    .willReturn(
                        okJson(createGetItemCategoryResult(VALID_ITEM_CATEGORY_ID))
                        
                    )
            );

        mockServer
            .stubFor(
                get(urlPathTemplate(itemCategoryServicePath + "/{id}"))
                .withPathParam("id", equalTo(SERVICE_UNAVAILABLE_ITEM_CATEGORY_ID))
                .willReturn(
                    serviceUnavailable()
                        
                )
            );

        mockServer.stubFor(
            get(urlPathTemplate(itemCategoryServicePath + "/{id}"))
            .withPathParam("id", equalTo(SERVICE_TIMEOUT_ITEM_CATEGORY_ID))
            .willReturn(
                aResponse()
                    .withFixedDelay(
                        (int)circuitBreakerConfig
                                .getSlowCallDurationThreshold().toMillis() + 1
                    )
                
            )
        );

        mockServer.stubFor(
            get(urlPathTemplate(itemCategoryServicePath + "/{id}"))
            .withPathParam("id", equalTo(NOT_FOUND_ITEM_CATEGORY_ID))
            .willReturn(
                notFound()
            )
        );
    }

    @BeforeEach
    public void setupForEach()
    {
        circuitBreaker.transitionToClosedState();
    }
    
    @Test
    public void should_ReturnItemCategoryById_When_ItemCategoryExists()
    {
        var result = getItemCategoryEndpointClient.getItemCategoryByIdSafely(VALID_ITEM_CATEGORY_ID);

        assertThat(result, is(notNullValue()));

        var itemCategory = result.getItemCategory();

        assertThat(itemCategory, is(notNullValue()));
        assertThat(itemCategory.getId(), is(VALID_ITEM_CATEGORY_ID));
        assertThat(itemCategory.getName(), is(not(emptyOrNullString())));
    }

    @Test
    public void should_RaiseError_When_ItemCategoryNotFound()
    {
        assertThrows(ItemCategoryNotFoundException.class, () -> {
            
            getItemCategoryEndpointClient
                .getItemCategoryByIdSafely(NOT_FOUND_ITEM_CATEGORY_ID);

        });
    }

    @Test
    public void should_CircuitBreaker_Be_Opened_When_ServiceUnavailable()
    {
        var endpointCallAttempts = circuitBreakerConfig.getSlidingWindowSize();

        assertThrows(CallNotPermittedException.class, () -> {

            IntStream.rangeClosed(1, endpointCallAttempts).forEach(i -> {

                try 
                {
                    getItemCategoryEndpointClient
                        .getItemCategoryByIdSafely(SERVICE_UNAVAILABLE_ITEM_CATEGORY_ID);

                } catch (EndpointIntegrationException e) 
                {
                    
                }

            });
            
        });
    }

    @Test
    public void should_CircuitBreaker_Be_Open_When_ServiceTimeout()
    {
        var endpointCallAttempts = circuitBreakerConfig.getSlidingWindowSize();

        assertThrows(CallNotPermittedException.class, () -> {

            IntStream.rangeClosed(1, endpointCallAttempts).forEach(i -> {

                getItemCategoryEndpointClient
                    .getItemCategoryByIdSafely(SERVICE_TIMEOUT_ITEM_CATEGORY_ID);

            });

        });
    }

    @SneakyThrows
    private String createGetItemCategoryResult(String id) 
    {
        var result = GetItemCategoryResult.of(ItemCategoryDto.of(id, id));

        return objectMapper.writeValueAsString(result);
    }
}
