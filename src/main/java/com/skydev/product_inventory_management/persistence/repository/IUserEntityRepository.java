package com.skydev.product_inventory_management.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skydev.product_inventory_management.persistence.entity.UserEntity;

@Repository
public interface IUserEntityRepository extends JpaRepository<UserEntity, Long>{

}
