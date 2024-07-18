package org.example.item_categories_service.application.features.get_item_category;

import java.net.http.HttpTimeoutException;
import java.util.ArrayList;
import java.util.SplittableRandom;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.item_categories_interfaces.GetItemCategoryEndpoint;
import org.example.item_categories_interfaces.GetItemCategoryResult;
import org.example.item_categories_interfaces.ItemCategoryDto;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/item-categories")
@Slf4j
public class HttpGetItemCategoryEndpoint implements GetItemCategoryEndpoint
{
    private SplittableRandom random = new SplittableRandom();

    private final GetItemCategoryFailuresProperties failuresProperties;

    @Override
    @GetMapping("/{id}")
    public GetItemCategoryResult getItemCategoryById(@PathVariable("id") String id) 
    {
        ensureInputIsValid(id);

        var probability = random.nextDouble();

        throwIfFailureFallIntoProbability(probability);

        return GetItemCategoryResult.of(
            ItemCategoryDto.of(
                id, 
                RandomStringUtils.randomAlphanumeric(10)
            )
        );
    }

    private void ensureInputIsValid(String id) 
    {
        if (!StringUtils.hasText(id))
        {
            throw new IllegalArgumentException("Item category's id isn't valid");
        }
    }

    private void throwIfFailureFallIntoProbability(double probability) 
    {
        var candidates = new ArrayList<Double>();

        if (failuresProperties.getItemCategoryNotFoundProbability() >= probability)
            candidates.add(failuresProperties.getItemCategoryNotFoundProbability());

        if (failuresProperties.getServiceTimeoutProbability() >= probability)
            candidates.add(failuresProperties.getServiceTimeoutProbability());

        if (failuresProperties.getServiceUnavailableProbability() >= probability)
            candidates.add(failuresProperties.getServiceUnavailableProbability());

        if (candidates.isEmpty())
            return;

        var winner = candidates.get(random.nextInt(candidates.size()));

        log.info("PROBABILITY: {}, WINNER: {}", probability, winner);

        if (winner == failuresProperties.getItemCategoryNotFoundProbability())
            throw new ItemCategoryNotFoundException();

        if (winner == failuresProperties.getServiceTimeoutProbability())
        {
            throw new FailureException(
                new HttpTimeoutException("Request's timeout is expired")
            );
        }

        if (winner == failuresProperties.getServiceUnavailableProbability())
        {
            throw new FailureException(
                new HttpServerErrorException(HttpStatus.SERVICE_UNAVAILABLE)
            );
        }
    }
}
