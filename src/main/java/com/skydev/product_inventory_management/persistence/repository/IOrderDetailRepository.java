package com.skydev.product_inventory_management.persistence.repository;

import com.skydev.product_inventory_management.persistence.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findAllByOrder_OrderId(Long orderOrderId);

}
