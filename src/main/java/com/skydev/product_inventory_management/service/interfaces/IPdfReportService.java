package com.skydev.product_inventory_management.service.interfaces;

import com.skydev.product_inventory_management.presentation.dto.request.order.RegisterOrderDTO;
import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.IOException;

public interface IPdfReportService {

    File generatePDF(Long userId, Long addressId, RegisterOrderDTO registerOrderDTO, Long orderId) throws JRException, IOException;

}
