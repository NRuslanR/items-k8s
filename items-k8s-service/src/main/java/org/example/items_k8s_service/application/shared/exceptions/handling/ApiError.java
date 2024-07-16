package org.example.items_k8s_service.application.shared.exceptions.handling;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
// @JsonTypeName("errors")
// @JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ApiError 
{
    public static ApiError of(String error)
    {
        return ApiError.of(List.of(error));
    }

    private List<String> errors;
}
