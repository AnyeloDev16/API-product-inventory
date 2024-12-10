package com.skydev.product_inventory_management.service.implementation;

import com.skydev.product_inventory_management.persistence.entity.Address;
import com.skydev.product_inventory_management.persistence.entity.UserEntity;
import com.skydev.product_inventory_management.presentation.dto.response.cart.ResponseCartDTO;
import com.skydev.product_inventory_management.presentation.dto.response.cart.ResponseCartItemDTO;
import com.skydev.product_inventory_management.presentation.dto.response.user.ResponseUserAdminDTO;
import com.skydev.product_inventory_management.service.interfaces.*;
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
    private final ICartService cartService;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public File generatePDF(Long userId, Long addressId, Long orderId) throws JRException, IOException {

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

            ResponseCartDTO findCart = cartService.findCartByUserId(userId);

            List<ItemOrderUtil> data = convertListItemOrder(cartService.getAllCartItemsByCartId(findCart.getCartId()));

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
            parameters.put("totalAmount", BigDecimal.valueOf(data.stream()
                    .mapToDouble(iou -> iou.getUnitPrice().doubleValue()*iou.getQuantity())
                    .sum()));
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
    public List<ItemOrderUtil> convertListItemOrder(List<ResponseCartItemDTO> cartItems) {

        return cartItems.stream()
                .map(ci -> ItemOrderUtil.builder()
                        .productId(ci.getProductId())
                        .productName(ci.getProductName())
                        .unitPrice(ci.getProductPrice())
                        .quantity(ci.getQuantity())
                        .subTotal(BigDecimal.valueOf(ci.getProductPrice().longValue() * ci.getQuantity()))
                        .build())
                .toList();
    }

}
