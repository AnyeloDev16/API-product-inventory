package com.skydev.product_inventory_management.service.implementation;

import com.skydev.product_inventory_management.persistence.entity.Address;
import com.skydev.product_inventory_management.persistence.entity.UserEntity;
import com.skydev.product_inventory_management.persistence.repository.IAddressRepository;
import com.skydev.product_inventory_management.presentation.dto.request.address.RegisterAddressDTO;
import com.skydev.product_inventory_management.presentation.dto.request.address.UpdateAddressDTO;
import com.skydev.product_inventory_management.presentation.dto.response.address.ResponseAddressDTO;
import com.skydev.product_inventory_management.service.exceptions.EntityNotFoundException;
import com.skydev.product_inventory_management.service.interfaces.IAddressService;
import com.skydev.product_inventory_management.util.EntityHelper;
import com.skydev.product_inventory_management.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {

    private final IAddressRepository addressRepository;
    private final ModelMapper modelMapper;
    private final MessageUtils messageUtils;

    @Override
    @Transactional
    public ResponseAddressDTO saveAddress(RegisterAddressDTO registerAddressDTO, Long userId) {

        Address address = modelMapper.map(registerAddressDTO, Address.class);

        address.setUser(UserEntity.builder().userId(userId).build());

        address = addressRepository.save(address);

        return modelMapper.map(address, ResponseAddressDTO.class);
    }

    @Override
    @Transactional
    public ResponseAddressDTO updateAddress(Long addressId, UpdateAddressDTO updateAddressDTO) {

        Address address = addressRepository.findById(addressId).orElseThrow( () ->
                                                    new EntityNotFoundException(messageUtils.ADDRESS_ID_NOT_FOUND));

        EntityHelper.updateAddress(address, updateAddressDTO);

        address = addressRepository.save(address);

        return modelMapper.map(address, ResponseAddressDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseAddressDTO getAddressById(Long addressId) {

        Address address = addressRepository.findById(addressId).orElseThrow( () ->
                                                    new EntityNotFoundException(messageUtils.ADDRESS_ID_NOT_FOUND));

        return modelMapper.map(address, ResponseAddressDTO.class);

    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseAddressDTO> getAllAddressByUserId(Long userId) {
        return addressRepository.findAllByUser_UserId(userId).stream()
                .map(address -> modelMapper.map(address, ResponseAddressDTO.class))
                .collect(Collectors.toList());
    }
}
