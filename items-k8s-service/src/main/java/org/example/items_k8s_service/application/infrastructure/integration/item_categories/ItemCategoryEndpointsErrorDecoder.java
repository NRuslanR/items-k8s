package org.example.items_k8s_service.application.infrastructure.integration.item_categories;

import org.example.items_k8s_service.application.infrastructure.integration.shared.BaseErrorDecoder;
import org.springframework.http.HttpStatus;

import feign.Response;

public class ItemCategoryEndpointsErrorDecoder extends BaseErrorDecoder 
{
    @Override
    public Exception decode(String methodKey, Response response) 
    {
        var responseStatus = HttpStatus.valueOf(response.status());

        if (responseStatus.isSameCodeAs(HttpStatus.NOT_FOUND))
            return new ItemCategoryNotFoundException();

        return super.decode(methodKey, response);
    }

}
