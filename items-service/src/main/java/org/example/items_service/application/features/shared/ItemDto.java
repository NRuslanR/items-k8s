package org.example.items_service.application.features.shared;

import org.example.item_categories_interfaces.ItemCategoryDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ItemDto 
{
    private String id;
    private String name;
    private ItemCategoryDto category;
}
