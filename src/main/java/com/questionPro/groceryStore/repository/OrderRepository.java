package com.questionPro.groceryStore.repository;

import com.questionPro.groceryStore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}