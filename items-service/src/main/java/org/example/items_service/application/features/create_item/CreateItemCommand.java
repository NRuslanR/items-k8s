package org.example.items_service.application.features.create_item;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class CreateItemCommand 
{
    @NotBlank
    private String name;

    @NotBlank
    private String categoryId;
}
