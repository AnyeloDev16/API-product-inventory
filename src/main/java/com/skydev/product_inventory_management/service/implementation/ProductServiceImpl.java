package com.skydev.product_inventory_management.service.implementation;

import com.skydev.product_inventory_management.persistence.entity.Category;
import com.skydev.product_inventory_management.persistence.entity.Product;
import com.skydev.product_inventory_management.persistence.repository.IProductPagingRepository;
import com.skydev.product_inventory_management.persistence.repository.IProductRepository;
import com.skydev.product_inventory_management.presentation.dto.relationDTO.IResponseProduct;
import com.skydev.product_inventory_management.presentation.dto.request.product.RegisterProductDTO;
import com.skydev.product_inventory_management.presentation.dto.request.product.UpdateProductDTO;
import com.skydev.product_inventory_management.presentation.dto.response.product.ResponseProductAdminDTO;
import com.skydev.product_inventory_management.service.exceptions.EntityNotFoundException;
import com.skydev.product_inventory_management.service.interfaces.ICategoryService;
import com.skydev.product_inventory_management.service.interfaces.IProductService;
import com.skydev.product_inventory_management.util.EntityHelper;
import com.skydev.product_inventory_management.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final IProductRepository productRepository;
    private final IProductPagingRepository productPagingRepository;
    private final ICategoryService categoryService;
    private final ModelMapper modelMapper;
    private final MessageUtils messageUtils;

    @Override
    @Transactional
    public ResponseProductAdminDTO saveProduct(RegisterProductDTO registerProductDTO) {

        Product newProduct = modelMapper.map(registerProductDTO, Product.class);

        Long categoryId = registerProductDTO.getCategoryId();

        Category findCategory = modelMapper.map(categoryService.findCategoryById(categoryId), Category.class);

        newProduct.setCategory(findCategory);

        if(newProduct.getStock() == null){
            newProduct.setStock(0);
        }

        newProduct.setActive(true);

        newProduct = productRepository.save(newProduct);

        return modelMapper.map(newProduct, ResponseProductAdminDTO.class);
    }

    @Override
    @Transactional
    public ResponseProductAdminDTO updateProduct(Long idProduct, UpdateProductDTO updateProductDTO) {

        Product findProduct = productRepository.findById(idProduct)
                                                    .orElseThrow( ()->
                                                            new EntityNotFoundException(messageUtils.PRODUCT_ID_NOT_FOUND));

        Long categoryId = updateProductDTO.getCategoryId();

        Category newCategory = null;

        if(categoryId != null){
            newCategory = modelMapper.map(categoryService.findCategoryById(categoryId), Category.class);
        }

        EntityHelper.updateProduct(findProduct, updateProductDTO, newCategory);

        findProduct = productRepository.save(findProduct);

        return modelMapper.map(findProduct, ResponseProductAdminDTO.class);

    }

    @Override
    @Transactional
    public void updateActive(Long idProduct) {

        Product findProduct = productRepository.findById(idProduct)
                .orElseThrow( ()->
                        new EntityNotFoundException(messageUtils.PRODUCT_ID_NOT_FOUND));

        findProduct.setActive(!findProduct.getActive());

        productRepository.save(findProduct);

    }

    @Override
    @Transactional(readOnly = true)
    public <T extends IResponseProduct> Page<T> findProductAll(Class<T> dtoClass, int pageNumber, int pageSize, Boolean active) {

        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        if(active == null){
            return productPagingRepository.findAll(pageable)
                    .map(pagPro -> modelMapper.map(pagPro, dtoClass));
        }

        return productPagingRepository.findAllByActive(active, pageable)
                    .map(pagPro -> modelMapper.map(pagPro, dtoClass));

    }

    @Override
    @Transactional(readOnly = true)
    public <T extends IResponseProduct> Page<T> findProductAllByProductName(Class<T> dtoClass, String productName, int pageNumber, int pageSize , Boolean active) {

        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        if(active == null){
            return productPagingRepository.findByProductNameContaining(productName, pageable)
                    .map(pagPro -> modelMapper.map(pagPro, dtoClass));
        }

        return productPagingRepository.findByProductNameContainingAndActive(productName, active, pageable)
                .map(pagPro -> modelMapper.map(pagPro, dtoClass));

    }

    @Override
    @Transactional(readOnly = true)
    public <T extends IResponseProduct> Page<T> findProductAllByCategoryId(Class<T> dtoClass, Long idCategory, int pageNumber, int pageSize, Boolean active) {

        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        if(active == null){
            return productPagingRepository.findAllByCategory_CategoryId(idCategory, pageable)
                    .map(pagPro -> modelMapper.map(pagPro, dtoClass));
        }

        return productPagingRepository.findAllByCategory_CategoryIdAndActive(idCategory, active, pageable)
                .map(pagPro -> modelMapper.map(pagPro, dtoClass));

    }

    @Override
    @Transactional(readOnly = true)
    public <T extends IResponseProduct> Page<T> findProductAllBetweenPrice(Class<T> dtoClass, BigDecimal minPrice, BigDecimal maxPrice, int pageNumber, int pageSize, Boolean active) {

        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNumber);

        if(active == null){
            return productPagingRepository.findAllBySalePriceBetween(minPrice, maxPrice, pageable)
                    .map(pagPro -> modelMapper.map(pagPro, dtoClass));
        }

        return productPagingRepository.findAllBySalePriceBetweenAndActive(minPrice, maxPrice, active, pageable)
                .map(pagPro -> modelMapper.map(pagPro, dtoClass));

    }

    @Override
    @Transactional(readOnly = true)
    public <T extends IResponseProduct> T findProductById(Long idProduct, Class<T> dtoClass) {

        Product findProduct = productRepository.findById(idProduct)
                .orElseThrow( ()->
                        new EntityNotFoundException(messageUtils.PRODUCT_ID_NOT_FOUND));

        return modelMapper.map(findProduct, dtoClass);
    }

}