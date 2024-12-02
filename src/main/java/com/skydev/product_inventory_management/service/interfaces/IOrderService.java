package com.skydev.product_inventory_management.service.interfaces;

import com.skydev.product_inventory_management.persistence.entity.enums.OrderStatus;
import com.skydev.product_inventory_management.presentation.dto.request.order.RegisterOrderDTO;
import com.skydev.product_inventory_management.presentation.dto.response.order.ResponseOrderBuyDTO;
import com.skydev.product_inventory_management.presentation.dto.response.order.ResponseOrderDTO;
import com.skydev.product_inventory_management.presentation.dto.response.order.ResponseOrderDetailDTO;
import com.skydev.product_inventory_management.presentation.dto.response.order.ResponseOrderWithOrderDetailsDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrderService {

    ResponseOrderBuyDTO buyOrder(RegisterOrderDTO registerOrderDTO, Long userId);
    void cancelOrder(Long orderId);
    ResponseOrderWithOrderDetailsDTO getOrderById(Long orderId);
    void changeOrderStatus(Long orderId, OrderStatus orderStatus);
    List<ResponseOrderDetailDTO> getAllProductsByOder(Long orderId);

    Page<ResponseOrderDTO> getAllOrdersByUser(Long userID, int pageNumber, int pageSize);
    Page<ResponseOrderDTO> getAllOrders(int pageNumber, int pageSize);

}
