package com.skydev.product_inventory_management.persistence.repository;

import com.skydev.product_inventory_management.persistence.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByUser_UserId(Long userId);

}
