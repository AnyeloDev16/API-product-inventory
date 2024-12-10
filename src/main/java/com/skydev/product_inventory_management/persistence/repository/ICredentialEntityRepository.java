package com.skydev.product_inventory_management.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.skydev.product_inventory_management.persistence.entity.CredentialEntity;

@Repository
public interface ICredentialEntityRepository extends JpaRepository<CredentialEntity, Long>{

    Optional<CredentialEntity> findByUser_userId(Long userId);
    Optional<CredentialEntity> findByUsername(String username);
    boolean existsByUsername(String username);

}
