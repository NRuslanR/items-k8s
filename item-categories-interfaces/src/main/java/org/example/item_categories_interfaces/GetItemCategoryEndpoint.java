package org.example.item_categories_interfaces;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface GetItemCategoryEndpoint 
{
    @GetMapping("/{id}")
    GetItemCategoryResult getItemCategoryById(@PathVariable("id") String id);
}
