package org.example.items_k8s_service.application.features.create_item;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

@WebMvcTest(controllers = CreateItemEndpoint.class)
public class CreateItemEndpointMockTests 
{
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    public void should_Return_SuccessfulCreateItemResponse_When_Request_Is_Valid()
    {
        var createItemCommand = CreateItemCommand.of("#1");

        mockMvc
            .perform(
                post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(createItemCommandContent(createItemCommand))
            )
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.item").isNotEmpty())
            .andExpect(jsonPath("$.item.id").isNotEmpty())
            .andExpect(jsonPath("$.item.name").value(createItemCommand.getName()));
    }

    @SneakyThrows
    private String createItemCommandContent(CreateItemCommand createItemCommand) 
    {
        return objectMapper.writeValueAsString(createItemCommand);
    }    
}
