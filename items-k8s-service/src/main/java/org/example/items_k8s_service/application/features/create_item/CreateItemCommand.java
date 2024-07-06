package org.example.items_k8s_service.application.features.create_item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class CreateItemCommand 
{
    private String name;
}
