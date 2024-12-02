package com.skydev.product_inventory_management.persistence.repository;

import com.skydev.product_inventory_management.persistence.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderPagingRepository extends PagingAndSortingRepository<Order, Long> {

    Page<Order> findAllByUser_UserId(Long userId, Pageable pageable);

}
