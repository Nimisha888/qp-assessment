package com.questionPro.groceryStore.service;

import com.questionPro.groceryStore.entity.GroceryItem;
import com.questionPro.groceryStore.entity.Orders;
import com.questionPro.groceryStore.entity.User;
import com.questionPro.groceryStore.repository.GroceryItemRepository;
import com.questionPro.groceryStore.repository.OrdersRepository;
import com.questionPro.groceryStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private GroceryItemRepository groceryItemRepository;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private OrdersRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    public List<GroceryItem> getAvailableGroceries() {
        return groceryItemRepository.findAll().stream()
                .filter(item -> item.getQuantity() > 0).collect(Collectors.toList());
    }

    public Orders placeOrder(Orders order, String token) {
        String email = tokenService.getEmailFromToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));

        order.setUser(user);
        order.setOrderDate(new Date());
        if (order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item.");
        }

        order.getOrderItems().forEach(orderItem -> {

            Integer groceryItemId = orderItem.getGroceryItem().getGroceryItemId();
            if (orderItem.getGroceryItem().getGroceryItemId() == null) {
                throw new IllegalArgumentException("Grocery item ID is null for order item: " + orderItem);
            }
            GroceryItem groceryItem = groceryItemRepository.findById(groceryItemId)
                    .orElseThrow(() -> new IllegalArgumentException("Grocery item not found: " + groceryItemId));

            if (groceryItem.getQuantity() < orderItem.getQuantity()) {
                throw new IllegalArgumentException("Insufficient inventory for item: " + groceryItem.getName());
            }

            groceryItem.setQuantity(groceryItem.getQuantity() - orderItem.getQuantity());
            groceryItemRepository.save(groceryItem);

            orderItem.setGroceryItem(groceryItem);
            orderItem.setOrder(order);
        });

        return orderRepository.save(order);
    }

}
