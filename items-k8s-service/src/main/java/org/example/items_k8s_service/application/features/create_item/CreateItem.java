package org.example.items_k8s_service.application.features.create_item;

import jakarta.validation.Valid;

public interface CreateItem 
{
    CreateItemResult run(@Valid CreateItemCommand command);
}
