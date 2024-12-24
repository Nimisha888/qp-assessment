package com.questionPro.groceryStore.repository;

import com.questionPro.groceryStore.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

}