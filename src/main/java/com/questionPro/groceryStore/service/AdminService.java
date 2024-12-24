package com.questionPro.groceryStore.service;

import com.questionPro.groceryStore.entity.GroceryItem;
import com.questionPro.groceryStore.repository.GroceryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private GroceryItemRepository groceryItemRepository;

    public GroceryItem addGroceryItem(GroceryItem groceryItem) {
        return groceryItemRepository.save(groceryItem);
    }

    public List<GroceryItem> getAllGroceries() {
        return groceryItemRepository.findAll();
    }

    public GroceryItem updateGroceryItem(Integer id, GroceryItem updatedGroceryItem) {
        GroceryItem existingGroceryItem = groceryItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Grocery item not found with ID: " + id));

        existingGroceryItem.setName(updatedGroceryItem.getName());
        existingGroceryItem.setPrice(updatedGroceryItem.getPrice());
        existingGroceryItem.setQuantity(updatedGroceryItem.getQuantity());

        return groceryItemRepository.save(existingGroceryItem);
    }

    public void deleteGroceryItem(Integer id) {
        if (!groceryItemRepository.existsById(id)) {
            throw new IllegalArgumentException("Grocery item not found with ID: " + id);
        }
        groceryItemRepository.deleteById(id);
    }

    public GroceryItem updateInventory(Integer id, int quantity) {
        GroceryItem groceryItem = groceryItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Grocery item not found with ID: " + id));

        groceryItem.setQuantity(quantity);
        return groceryItemRepository.save(groceryItem);
    }
}
