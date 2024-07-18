package org.example.items_service.application.features.create_item;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.stream.Stream;

import org.example.items_service.application.infrastructure.integration.item_categories.GetItemCategoryEndpointClient;
import org.example.items_service.shared.config.MockIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

@MockIntegrationTest
@Import(CreateItemEndpointMockTestsConfig.class)
@EnableAutoConfiguration(
    exclude = {
        DataSourceAutoConfiguration.class
    }
)
@SpringBootTest(classes = {
    GetItemCategoryEndpointClient.class,
    CreateItemEndpoint.class
})
@AutoConfigureMockMvc
public class CreateItemEndpointMockTests 
{
    @Autowired
    private CreateItemMockTestsConfig createItemMockTestsConfig;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setupForEach()
    {
        createItemMockTestsConfig
            .getGetItemCategoryEndpointClientMockTestsConfig()
            .resetFailedStates();
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("correctCreateItemRequests")
    public void should_Return_SuccessfulCreateItemResponse_When_Request_Is_Valid(CreateItemCommand createItemCommand)
    {
        mockMvc
            .perform(
                post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(createItemCommandContent(createItemCommand))
            )
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.item").isNotEmpty())
            .andExpect(jsonPath("$.item.id").isNotEmpty())
            .andExpect(jsonPath("$.item.name").value(createItemCommand.getName()))
            .andExpect(jsonPath("$.item.category").isNotEmpty())
            .andExpect(jsonPath("$.item.category.id").value(createItemCommand.getCategoryId()))
            .andExpect(jsonPath("$.item.category.name").isNotEmpty());
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("inCorrectCreateItemRequests")
    public void should_Return_FailedCreateItemResponse_When_Request_Is_InValid(
        CreateItemCommand inCorrectCommand
    )
    {
        mockMvc
            .perform(
                post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(createItemCommandContent(inCorrectCommand))
            )
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors", is(not(empty()))));
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("createItemRequestsForFailedIntegration")
    public void should_Return_FailedCreateItemResponse_When_ThirdPartyIntegrationIsFailed(
        CreateItemCommand failedIntegrationCommand
    )
    {
        mockMvc
            .perform(
                post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(createItemCommandContent(failedIntegrationCommand))
            )
            .andExpect(status().is(
                anyOf(
                    is(HttpStatus.NOT_FOUND.value()), 
                    is(HttpStatus.SERVICE_UNAVAILABLE.value()))
                )
            )
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors", is(not(empty()))));
    }

    @SneakyThrows
    @ParameterizedTest
    @MethodSource("createItemRequestsForCircuitBreakerOpening")
    public void should_Return_FailedCreateItemResponse_When_CircuitBreakerIsOpen(
        CreateItemCommand circuitBreakerOpenCommand
    )
    {
        createItemMockTestsConfig
            .getGetItemCategoryEndpointClientMockTestsConfig()
            .getCircuitBreaker()
            .transitionToOpenState();

        mockMvc
            .perform(
                post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(createItemCommandContent(circuitBreakerOpenCommand))
            )
            .andExpect(status().isServiceUnavailable())
            .andExpect(jsonPath("$.errors").exists())
            .andExpect(jsonPath("$.errors", is(not(empty()))));
    }

    private Stream<Arguments> correctCreateItemRequests()
    {
        return createItemMockTestsConfig.correctCreateItemCommands().stream().map(Arguments::of);
    }

    private Stream<Arguments> inCorrectCreateItemRequests()
    {
        return createItemMockTestsConfig.inCorrectCreateItemCommands().stream().map(Arguments::of);
    }

    private Stream<Arguments> createItemRequestsForFailedIntegration()
    {
        return createItemMockTestsConfig.createItemCommandsForFailedIntegration().stream().map(Arguments::of);
    }

    private Stream<Arguments> createItemRequestsForCircuitBreakerOpening()
    {
        return createItemMockTestsConfig.createItemCommandsForCircuitBreakerOpening().stream().map(Arguments::of);
    }

    @SneakyThrows
    private String createItemCommandContent(CreateItemCommand createItemCommand) 
    {
        return objectMapper.writeValueAsString(createItemCommand);
    }    
}
