package com.skydev.product_inventory_management.persistence.entity;

import com.skydev.product_inventory_management.persistence.entity.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_methods",
       uniqueConstraints = {
               @UniqueConstraint(columnNames = {"provider_id", "payment_type"})
       })
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private Long paymentMethodId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id", nullable = false)
    private PaymentProvider paymentProvider;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @Column(nullable = false)
    private boolean active;

}
