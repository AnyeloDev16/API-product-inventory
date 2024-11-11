package com.skydev.product_inventory_management.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.skydev.product_inventory_management.persistence.entity.RoleEntity;
import com.skydev.product_inventory_management.presentation.dto.request.RegisterUserAuthDTO;

@Configuration

@PropertySource("classpath:title-messages.properties")
@PropertySource("classpath:messages.properties")
public class AppConfig {

    @Bean
    public ModelMapper getModelMapper(){

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(RegisterUserAuthDTO.class, RoleEntity.class)
                .addMapping(RegisterUserAuthDTO::getRole, (des, v) -> des.setRoleName((String)v));

        return modelMapper;

    }

}

