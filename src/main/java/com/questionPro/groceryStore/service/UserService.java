package com.questionPro.groceryStore.service;

import com.questionPro.groceryStore.entity.GroceryItem;
import com.questionPro.groceryStore.entity.Order;
import com.questionPro.groceryStore.repository.GroceryItemRepository;
import com.questionPro.groceryStore.repository.OrderRepository;
import com.questionPro.groceryStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private GroceryItemRepository groceryItemRepository;


    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    public List<GroceryItem> getAvailableGroceries() {
        return groceryItemRepository.findAll().stream()
                .filter(item -> item.getQuantity() > 0).collect(Collectors.toList());
    }

    public Order placeOrder(Order order) {
        order.setOrderDate(LocalDateTime.now());

        order.getOrderItems().forEach(orderItem -> {
            GroceryItem groceryItem = orderItem.getGroceryItem();
            int newQuantity = groceryItem.getQuantity() - orderItem.getQuantity();

            if (newQuantity < 0) {
                throw new IllegalArgumentException("Insufficient stock for item: " + groceryItem.getName());
            }

            groceryItem.setQuantity(newQuantity);
            groceryItemRepository.save(groceryItem);
        });

        return orderRepository.save(order);
    }
}
