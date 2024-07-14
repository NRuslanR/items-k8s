package org.example.items_k8s_service.application.infrastructure.integration.item_categories.config;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.notFound;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.serviceUnavailable;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathTemplate;

import org.example.item_categories_interfaces.GetItemCategoryResult;
import org.example.item_categories_interfaces.ItemCategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;
import wiremock.org.apache.commons.lang3.RandomStringUtils;

@Data
@TestConfiguration
@Import({
    ItemCategoryMockServerConfig.class,
    ItemCategoryServiceInstanceListSupplier.class,
    ConstantUseCaseItemCategoryIds.class
})
public class GetItemCategoryEndpointClientMockTestsConfig 
{
    @Autowired
    private WireMockServer mockServer;

    @Autowired
    @Delegate
    private UseCaseItemCategoryIds ids;

    @Autowired
    private ObjectMapper objectMapper;
    
    private CircuitBreaker circuitBreaker;
    private CircuitBreakerConfig circuitBreakerConfig;

    @Value("${item-categories-service.path}")
    private String itemCategoryServicePath;

    public GetItemCategoryEndpointClientMockTestsConfig(@Autowired CircuitBreakerRegistry circuitBreakerRegistry)
    {
        circuitBreaker = circuitBreakerRegistry.circuitBreaker("item-categories-cb");
        circuitBreakerConfig = circuitBreaker.getCircuitBreakerConfig();
    }

    @SneakyThrows
    @PostConstruct
    public void initEndpointStubs()
    {
        var getItemCategoryResult = 
            GetItemCategoryResult.of(
                validItemCategoryInstance()
            );

        mockServer
            .stubFor(
                get(urlPathTemplate(itemCategoryServicePath + "/{id}"))
                    .withPathParam("id", equalTo(ids.validItemCategoryId()))
                    .willReturn(
                        okJson(
                            objectMapper.writeValueAsString(getItemCategoryResult)
                        )
                        
                    )
            );

        mockServer
            .stubFor(
                get(urlPathTemplate(itemCategoryServicePath + "/{id}"))
                .withPathParam("id", equalTo(ids.serviceUnavailableItemCategoryId()))
                .willReturn(
                    serviceUnavailable()
                        
                )
            );

        mockServer.stubFor(
            get(urlPathTemplate(itemCategoryServicePath + "/{id}"))
            .withPathParam("id", equalTo(ids.serviceTimeoutItemCategoryId()))
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
            .withPathParam("id", equalTo(ids.notFoundItemCategoryId()))
            .willReturn(
                notFound()
            )
        );
    }

    public ItemCategoryDto validItemCategoryInstance()
    {
        return ItemCategoryDto.of(validItemCategoryId(), RandomStringUtils.random(10));
    }
}
