package com.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.order.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}

