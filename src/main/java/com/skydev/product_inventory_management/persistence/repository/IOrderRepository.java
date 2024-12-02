package com.skydev.product_inventory_management.persistence.repository;

import com.skydev.product_inventory_management.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {



}
