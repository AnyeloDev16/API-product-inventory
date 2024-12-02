package com.skydev.product_inventory_management.persistence.repository;

import com.skydev.product_inventory_management.persistence.entity.PaymentProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPaymentProviderRepository extends JpaRepository<PaymentProvider, Long> {



}
