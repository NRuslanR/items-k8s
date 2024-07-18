package org.example.item_categories_service.application.features.get_item_category;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.failures.endpoints.get-item-categories")
public class GetItemCategoryFailuresProperties 
{
    private double serviceUnavailableProbability;
    private double serviceTimeoutProbability;
    private double itemCategoryNotFoundProbability;
}
