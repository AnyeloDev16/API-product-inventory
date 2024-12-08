package com.skydev.product_inventory_management.service.implementation;

import com.skydev.product_inventory_management.persistence.entity.Address;
import com.skydev.product_inventory_management.persistence.entity.UserEntity;
import com.skydev.product_inventory_management.presentation.dto.request.order.RegisterOrderDTO;
import com.skydev.product_inventory_management.presentation.dto.request.order.RegisterOrderDetailDTO;
import com.skydev.product_inventory_management.presentation.dto.response.product.ResponseProductUserDTO;
import com.skydev.product_inventory_management.presentation.dto.response.user.ResponseUserAdminDTO;
import com.skydev.product_inventory_management.service.interfaces.IAddressService;
import com.skydev.product_inventory_management.service.interfaces.IPdfReportService;
import com.skydev.product_inventory_management.service.interfaces.IProductService;
import com.skydev.product_inventory_management.service.interfaces.IUserEntityService;
import com.skydev.product_inventory_management.util.ItemOrderUtil;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PdfReportServiceImpl implements IPdfReportService {

    private final IUserEntityService userEntityService;
    private final IAddressService addressService;
    private final IProductService productService;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public File generatePDF(Long userId, Long addressId, RegisterOrderDTO registerOrderDTO, Long orderId) throws JRException, IOException {

        UserEntity user = modelMapper.map(userEntityService.findUserById(userId, ResponseUserAdminDTO.class), UserEntity.class);
        Address address = modelMapper.map(addressService.getAddressById(addressId), Address.class);

        try (InputStream companyLogo = getClass().getResourceAsStream("/reports/img/company-logo.png");
             InputStream gitHubLogo = getClass().getResourceAsStream("/reports/img/github-logo.png");
             InputStream gmailLogo = getClass().getResourceAsStream("/reports/img/gmail-logo.png");
             InputStream report = getClass().getResourceAsStream("/reports/Invoice.jasper")) {

            if (companyLogo == null || gitHubLogo == null || gmailLogo == null) {
                throw new FileNotFoundException("Una o más imágenes no se encuentran en el directorio /reports/img/");
            }

            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(report);

            List<ItemOrderUtil> data = convertListItemOrder(registerOrderDTO.getOrderDetails());

            JRBeanCollectionDataSource dsPurchaseDetailReport = new JRBeanCollectionDataSource(data);

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("dsPurchaseDetailReport", dsPurchaseDetailReport);
            parameters.put("companyLogo", companyLogo);
            parameters.put("orderId", orderId);
            parameters.put("fullName", user.getName() + " " + user.getFirstLastName() + " " + user.getSecondLastName());
            parameters.put("email", user.getEmail());
            parameters.put("phone", (user.getPhone() == null ? "" : user.getPhone()));
            parameters.put("addressLine", address.getAddressLine());
            parameters.put("street", address.getStreet());
            parameters.put("district", address.getDistrict());
            parameters.put("department", address.getDepartment());
            parameters.put("country", address.getCountry());
            parameters.put("zipCode", address.getZipCode());
            parameters.put("totalAmount", registerOrderDTO.getTotalAmount());
            parameters.put("gitHubLogo", gitHubLogo);
            parameters.put("gmailLogo", gmailLogo);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            byte[] arrayPdf = JasperExportManager.exportReportToPdf(jasperPrint);

            File pdfFile = new File("Invoice-" + orderId + ".pdf");

            try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
                fos.write(arrayPdf);
            }

            return pdfFile;

        } catch (FileNotFoundException e) {
            throw new IOException("Error al generar el archivo PDF: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<ItemOrderUtil> convertListItemOrder(List<RegisterOrderDetailDTO> orderDetails) {

        return orderDetails.stream()
                .map(od -> ItemOrderUtil.builder()
                        .productId(od.getProductId())
                        .productName(productService.findProductById(od.getProductId(), ResponseProductUserDTO.class).getProductName())
                        .unitPrice(od.getUnitPrice())
                        .quantity(od.getQuantity())
                        .subTotal(BigDecimal.valueOf(od.getUnitPrice().longValue() * od.getQuantity()))
                        .build())
                .toList();
    }

}
