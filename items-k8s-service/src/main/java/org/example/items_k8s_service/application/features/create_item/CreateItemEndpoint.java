package org.example.items_k8s_service.application.features.create_item;

import java.util.UUID;

import org.example.items_k8s_service.application.features.shared.ItemDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
public class CreateItemEndpoint 
{
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CreateItemResult createItem(@RequestBody CreateItemCommand command) 
    {
        return CreateItemResult.of(ItemDto.of(UUID.randomUUID().toString(), command.getName()));
    }
    
}
