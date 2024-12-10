package com.skydev.product_inventory_management.service.interfaces;

import com.skydev.product_inventory_management.presentation.dto.request.address.RegisterAddressDTO;
import com.skydev.product_inventory_management.presentation.dto.request.address.UpdateAddressDTO;
import com.skydev.product_inventory_management.presentation.dto.response.address.ResponseAddressDTO;

import java.util.List;

public interface IAddressService {

    ResponseAddressDTO saveAddress(RegisterAddressDTO registerAddressDTO, Long userId);
    ResponseAddressDTO updateAddress(Long addressId, UpdateAddressDTO updateAddressDTO);
    ResponseAddressDTO getAddressById(Long addressId);
    List<ResponseAddressDTO> getAllAddressByUserId(Long userId);

}
