package org.example.item_categories_service.application.features.shared.exceptions;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class ApiError 
{
    public static ApiError of(String error)
    {
        return ApiError.of(List.of(error));
    }

    private List<String> errors;
}
