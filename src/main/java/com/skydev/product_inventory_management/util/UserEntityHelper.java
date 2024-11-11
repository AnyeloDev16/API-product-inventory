package com.skydev.product_inventory_management.util;

import com.skydev.product_inventory_management.persistence.entity.UserEntity;
import com.skydev.product_inventory_management.persistence.entity.enums.Gender;
import com.skydev.product_inventory_management.presentation.dto.request.UpdateUserDTO;

import java.time.LocalDate;

public class UserEntityHelper {

    private UserEntityHelper() {
        throw new IllegalStateException("Utility class");
    }
    
    public static void updateUser(UserEntity user, UpdateUserDTO updateUser){

        String name = updateUser.getName();
        String lastFirstName = updateUser.getFirstLastName();
        String lastSecondName = updateUser.getSecondLastName();
        String gender = updateUser.getGender();
        LocalDate birthDate = updateUser.getBirthDate();
        String phone = updateUser.getPhone();

        if(name != null){
            user.setName(name);
        }

        if(lastFirstName != null){
            user.setFirstLastName(lastFirstName);
        }

        if(lastSecondName != null){
            user.setSecondLastName(lastSecondName);
        }

        if(gender != null){
            user.setGender(Gender.convertToGender(gender));
        }

        if(birthDate != null){
            user.setBirthDate(birthDate);
        }

        if(phone != null){
            user.setPhone(phone);
        }

    }

}
