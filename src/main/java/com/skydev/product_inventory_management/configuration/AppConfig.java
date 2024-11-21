package com.skydev.product_inventory_management.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.skydev.product_inventory_management.persistence.entity.Product;
import com.skydev.product_inventory_management.presentation.dto.request.product.RegisterProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.skydev.product_inventory_management.persistence.entity.RoleEntity;
import com.skydev.product_inventory_management.presentation.dto.request.user.RegisterUserAuthDTO;

@Configuration
@PropertySource("classpath:title-messages.properties")
@PropertySource("classpath:messages.properties")
public class AppConfig {

    @Bean
    public ModelMapper getModelMapper(){

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.createTypeMap(RegisterUserAuthDTO.class, RoleEntity.class)
                .addMapping(RegisterUserAuthDTO::getRole, (des, v) -> des.setRoleName((String)v));

        modelMapper.createTypeMap(RegisterProductDTO.class, Product.class)
                .addMappings(map -> map.skip(Product::setProductId));

        return modelMapper;

    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper;
    }

}

