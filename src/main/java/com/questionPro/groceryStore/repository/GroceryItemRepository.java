package com.questionPro.groceryStore.repository;

import com.questionPro.groceryStore.entity.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryItemRepository extends JpaRepository<GroceryItem, Long> {

}