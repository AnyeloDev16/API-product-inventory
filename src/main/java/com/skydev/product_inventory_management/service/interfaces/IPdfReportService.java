package com.skydev.product_inventory_management.service.interfaces;

import net.sf.jasperreports.engine.JRException;

import java.io.File;
import java.io.IOException;

public interface IPdfReportService {

    File generatePDF(Long userId, Long addressId, Long orderId) throws JRException, IOException;

}
