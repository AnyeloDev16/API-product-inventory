package com.skydev.product_inventory_management.persistence.entity;

import java.time.LocalDate;

import com.skydev.product_inventory_management.persistence.entity.enums.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(name = "first_last_name", nullable = false, length = 100)
    private String firstLastName;
    
    @Column(name = "second_last_name", nullable = false, length = 100)
    private String secondLastName;
    
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
    
    @Column(name = "birth_date", nullable = false, columnDefinition = "DATE")
    private LocalDate birthDate;
    
    @Column(nullable = false, length = 100, unique = true)
    private String email;
    
    @Column(length = 20, unique = true)
    private String phone;
    
    @Column(nullable = false)
    private Boolean active;

}
