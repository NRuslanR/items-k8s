package org.example.item_categories_service.application.features.get_item_category;

public class ItemCategoryNotFoundException extends RuntimeException
{
    public ItemCategoryNotFoundException() {
        super("item category not found");
    }
 
    public ItemCategoryNotFoundException(String message) {
       super(message);
    }
}
