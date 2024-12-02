package com.skydev.product_inventory_management.service.implementation;

import com.skydev.product_inventory_management.persistence.entity.PaymentMethod;
import com.skydev.product_inventory_management.persistence.entity.PaymentProvider;
import com.skydev.product_inventory_management.persistence.repository.IPaymentMethodRepository;
import com.skydev.product_inventory_management.persistence.repository.IPaymentProviderRepository;
import com.skydev.product_inventory_management.presentation.dto.response.payment.ResponsePaymentMethodDTO;
import com.skydev.product_inventory_management.presentation.dto.response.payment.ResponsePaymentMethodUserDTO;
import com.skydev.product_inventory_management.presentation.dto.response.payment.ResponsePaymentProviderDTO;
import com.skydev.product_inventory_management.service.exceptions.EntityNotFoundException;
import com.skydev.product_inventory_management.service.interfaces.IPaymentService;
import com.skydev.product_inventory_management.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {

    private final IPaymentMethodRepository paymentMethodRepository;
    private final IPaymentProviderRepository paymentProviderRepository;
    private final ModelMapper modelMapper;
    private final MessageUtils messageUtils;

    @Override
    public ResponsePaymentProviderDTO getPaymentProviderById(Long idPaymentProvider) {

        PaymentProvider paymentProvider = paymentProviderRepository.findById(idPaymentProvider).orElseThrow( () ->
                                                    new EntityNotFoundException(messageUtils.PAYMENT_PROVIDER_ID_NOT_FOUND));

        List<ResponsePaymentMethodDTO> listPaymentMethod = paymentMethodRepository.findAllByPaymentProvider_ProviderId(idPaymentProvider)
                .stream()
                .map(method -> modelMapper.map(method, ResponsePaymentMethodDTO.class))
                .collect(Collectors.toList());

        return ResponsePaymentProviderDTO.builder()
                .providerName(paymentProvider.getProviderName())
                .listPaymentMethod(listPaymentMethod)
                .build();
    }

    @Override
    public List<ResponsePaymentProviderDTO> getAllPaymentProviders() {
        return paymentProviderRepository.findAll().stream()
                .map(provider -> {

                    List<ResponsePaymentMethodDTO> listPaymentMethod = paymentMethodRepository.findAllByPaymentProvider_ProviderId(provider.getProviderId())
                            .stream()
                            .map(method -> modelMapper.map(method, ResponsePaymentMethodDTO.class))
                            .collect(Collectors.toList());

                    return ResponsePaymentProviderDTO.builder()
                            .providerName(provider.getProviderName())
                            .listPaymentMethod(listPaymentMethod)
                            .build();

                })
                .collect(Collectors.toList());
    }

    @Override
    public ResponsePaymentMethodUserDTO getPaymentMethodById(Long idPaymentMethod) {

        PaymentMethod paymentMethod = paymentMethodRepository.findById(idPaymentMethod).orElseThrow(() ->
                                                    new EntityNotFoundException(messageUtils.PAYMENT_METHOD_ID_NOT_FOUND));

        String paymentProvider = paymentMethod.getPaymentProvider().getProviderName();

        return ResponsePaymentMethodUserDTO.builder()
                .paymentProvider(paymentProvider)
                .paymentType(paymentMethod.getPaymentType().getValue())
                .active(paymentMethod.isActive())
                .build();
    }

}
