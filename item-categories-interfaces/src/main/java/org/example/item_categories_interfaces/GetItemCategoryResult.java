package org.example.item_categories_interfaces;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class GetItemCategoryResult 
{
    private ItemCategoryDto itemCategory;
}
