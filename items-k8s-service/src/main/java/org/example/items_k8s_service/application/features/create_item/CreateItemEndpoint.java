package org.example.items_k8s_service.application.features.create_item;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/items")
public class CreateItemEndpoint 
{
    private final CreateItem createItem;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CreateItemResult createItem(@RequestBody CreateItemCommand command) 
    {
        return createItem.run(command);
    }
}
