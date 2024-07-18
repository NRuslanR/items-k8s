package org.example.items_service.application.features.create_item;

import org.example.items_service.application.features.shared.ItemDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class CreateItemResult 
{
    private ItemDto item;
}
