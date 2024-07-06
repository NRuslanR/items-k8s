package org.example.items_k8s_service.application.features.shared;

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
}
