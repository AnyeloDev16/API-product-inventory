package com.skydev.product_inventory_management.service.implementation;

import com.skydev.product_inventory_management.persistence.entity.*;
import com.skydev.product_inventory_management.persistence.entity.enums.OrderStatus;
import com.skydev.product_inventory_management.persistence.repository.IOrderDetailRepository;
import com.skydev.product_inventory_management.persistence.repository.IOrderPagingRepository;
import com.skydev.product_inventory_management.persistence.repository.IOrderRepository;
import com.skydev.product_inventory_management.presentation.dto.request.order.RegisterOrderDTO;
import com.skydev.product_inventory_management.presentation.dto.response.order.ResponseOrderBuyDTO;
import com.skydev.product_inventory_management.presentation.dto.response.order.ResponseOrderDTO;
import com.skydev.product_inventory_management.presentation.dto.response.order.ResponseOrderDetailDTO;
import com.skydev.product_inventory_management.presentation.dto.response.order.ResponseOrderWithOrderDetailsDTO;
import com.skydev.product_inventory_management.service.exceptions.EntityNotFoundException;
import com.skydev.product_inventory_management.service.interfaces.IOrderService;
import com.skydev.product_inventory_management.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IOrderDetailRepository orderDetailRepository;
    private final IOrderPagingRepository orderPagingRepository;
    private final MessageUtils messageUtils;

    @Override
    @Transactional
    public ResponseOrderBuyDTO buyOrder(RegisterOrderDTO registerOrderDTO, Long userId) {

        Order order = Order.builder()
                .user(UserEntity.builder().userId(userId).build())
                .totalAmount(registerOrderDTO.getTotalAmount())
                .orderDate(LocalDateTime.now())
                .paymentMethod(PaymentMethod.builder().paymentMethodId(registerOrderDTO.getPaymentMethodId()).build())
                .orderStatus(OrderStatus.PAID)
                .build();

        orderRepository.save(order);

        List<OrderDetail> listOrderDetails = registerOrderDTO.getOrderDetails().stream()
                .map(responseOD -> OrderDetail.builder()
                        .order(Order.builder().orderId(order.getOrderId()).build())
                        .product(Product.builder().productId(responseOD.getProductId()).build())
                        .unitPrice(responseOD.getUnitPrice())
                        .quantity(responseOD.getQuantity())
                        .build())
                .toList();

        orderDetailRepository.saveAll(listOrderDetails);

        return ResponseOrderBuyDTO.builder()
                .message(messageUtils.ORDER_SUCCESSFUL_PURCHASE)
                .orderID(order.getOrderId())
                .orderStatus(OrderStatus.PAID.toString())
                .build();

    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException(messageUtils.ORDER_ID_NOT_FOUND));

        order.setOrderStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

    }

    @Override
    @Transactional(readOnly = true)
    public ResponseOrderWithOrderDetailsDTO getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException(messageUtils.ORDER_ID_NOT_FOUND));

        ResponseOrderDTO responseOrderDTO = ResponseOrderDTO.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getUserId())
                .totalAmount(order.getTotalAmount())
                .orderDate(order.getOrderDate())
                .paymentProvider(order.getPaymentMethod().getPaymentProvider().getProviderName())
                .paymentMethod(order.getPaymentMethod().getPaymentType().getValue())
                .orderStatus(order.getOrderStatus().getValue())
                .build();

        List<ResponseOrderDetailDTO> orderDetail = this.getAllProductsByOder(orderId);

        return ResponseOrderWithOrderDetailsDTO.builder()
                .orderDetails(responseOrderDTO)
                .listProduct(orderDetail)
                .build();

    }

    @Override
    @Transactional
    public void changeOrderStatus(Long orderId, OrderStatus orderStatus) {

        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new EntityNotFoundException(messageUtils.ORDER_ID_NOT_FOUND));

        order.setOrderStatus(orderStatus);

        orderRepository.save(order);

    }

    @Override
    @Transactional(readOnly = true)
    public List<ResponseOrderDetailDTO> getAllProductsByOder(Long orderId) {

        boolean orderExists = orderRepository.existsById(orderId);

        if(!orderExists) {
            throw new EntityNotFoundException(messageUtils.ORDER_ID_NOT_FOUND);
        }

        return orderDetailRepository.findAllByOrder_OrderId(orderId).stream()
                .map(order -> ResponseOrderDetailDTO.builder()
                        .productName(order.getProduct().getProductName())
                        .unitPrice(order.getUnitPrice())
                        .quantity(order.getQuantity())
                        .totalPrice(BigDecimal.valueOf(order.getUnitPrice().longValue() * order.getQuantity()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseOrderDTO> getAllOrdersByUser(Long userID, int pageNumber, int pageSize) {

        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        return orderPagingRepository.findAllByUser_UserId(userID, pageable)
                .map(order -> ResponseOrderDTO.builder()
                        .orderId(order.getOrderId())
                        .userId(order.getUser().getUserId())
                        .totalAmount(order.getTotalAmount())
                        .orderDate(order.getOrderDate())
                        .paymentProvider(order.getPaymentMethod().getPaymentProvider().getProviderName())
                        .paymentMethod(order.getPaymentMethod().getPaymentType().getValue())
                        .orderStatus(order.getOrderStatus().getValue())
                        .build());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ResponseOrderDTO> getAllOrders(int pageNumber, int pageSize) {

        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        return orderPagingRepository.findAll(pageable)
                .map(order -> ResponseOrderDTO.builder()
                        .orderId(order.getOrderId())
                        .userId(order.getUser().getUserId())
                        .totalAmount(order.getTotalAmount())
                        .orderDate(order.getOrderDate())
                        .paymentProvider(order.getPaymentMethod().getPaymentProvider().getProviderName())
                        .paymentMethod(order.getPaymentMethod().getPaymentType().getValue())
                        .orderStatus(order.getOrderStatus().getValue())
                        .build());
    }

}
