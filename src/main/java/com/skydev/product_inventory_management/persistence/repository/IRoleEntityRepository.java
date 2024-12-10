package com.skydev.product_inventory_management.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skydev.product_inventory_management.persistence.entity.RoleEntity;

@Repository
public interface IRoleEntityRepository extends JpaRepository<RoleEntity, Long>{

    Optional<RoleEntity> findByRoleName(String roleName);

}
