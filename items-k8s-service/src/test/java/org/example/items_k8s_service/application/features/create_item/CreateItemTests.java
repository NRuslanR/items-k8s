package org.example.items_k8s_service.application.features.create_item;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThrows;

import java.util.stream.Stream;

import org.example.items_k8s_service.application.infrastructure.integration.shared.EndpointIntegrationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import jakarta.validation.ConstraintViolationException;

public abstract class CreateItemTests 
{
    @Autowired
    private CreateItemTestsConfig config;
    
    @Autowired
    private CreateItem createItem;

    @BeforeEach
    public void setupForEach()
    {
        config.getGetItemCategoryEndpointClientMockTestsConfig().resetFailedStates();
    }

    @ParameterizedTest
    @MethodSource("correctCreateItemCommands")
    public void should_CreateItem_When_CommandIsValid(CreateItemCommand command)
    {
        var result = createItem.run(command);

        assertThat(result, is(notNullValue()));

        var item = result.getItem();

        assertThat(item, is(notNullValue()));
        assertThat(item.getId(), is(not(emptyOrNullString())));
        assertThat(item.getName(), is(command.getName()));

        var category = item.getCategory();

        assertThat(category, is(notNullValue()));

        assertThat(category.getId(), is(command.getCategoryId()));
        assertThat(category.getName(), is(not(emptyOrNullString())));
    }    

    @ParameterizedTest
    @MethodSource("inCorrectCreateItemCommands")
    public void should_RaiseError_When_CommandIsInCorrect(CreateItemCommand incorrectCommand)
    {
        assertThrows(ConstraintViolationException.class, () -> {

            createItem.run(incorrectCommand);

        });
    }

    @ParameterizedTest
    @MethodSource("createItemCommandsForFailedIntegration")
    public void should_RaiseError_When_IntegrationFailed(CreateItemCommand failedIntegrationCommand)
    {
        assertThrows(EndpointIntegrationException.class, () -> {

            createItem.run(failedIntegrationCommand);

        });
    }

    @ParameterizedTest
    @MethodSource("createItemCommandsForCircuitBreakerOpening")
    public void should_RaiseError_When_CircuitBreakerIsOpen(CreateItemCommand circuitBreakerOpenCommand)
    {
        assertThrows(CallNotPermittedException.class, () -> {

            config
                .getGetItemCategoryEndpointClientMockTestsConfig()
                .executeToOpenCircuitBreaker(() -> {

                    createItem.run(circuitBreakerOpenCommand);

                });
        
        });
    }

    private Stream<Arguments> correctCreateItemCommands()
    {
        return config.correctCreateItemCommands().stream().map(Arguments::of);
    }

    private Stream<Arguments> inCorrectCreateItemCommands()
    {
        return config.inCorrectCreateItemCommands().stream().map(Arguments::of);
    }

    private Stream<Arguments> createItemCommandsForFailedIntegration()
    {
        return config.createItemCommandsForFailedIntegration().stream().map(Arguments::of);
    }

    private Stream<Arguments> createItemCommandsForCircuitBreakerOpening()
    {
        return config.createItemCommandsForCircuitBreakerOpening().stream().map(Arguments::of);
    }
}
