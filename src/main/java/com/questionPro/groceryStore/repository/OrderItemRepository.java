package com.questionPro.groceryStore.repository;

import com.questionPro.groceryStore.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
