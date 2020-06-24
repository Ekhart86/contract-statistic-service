package ru.ekhart86.contractservice.facade;

import org.springframework.stereotype.Service;
import ru.ekhart86.contractservice.domain.ProductDTO;
import ru.ekhart86.contractservice.entity.Product;
import ru.ekhart86.contractservice.service.product.ProductServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductFacade {

    private final ProductServiceImpl productService;

    public ProductFacade(ProductServiceImpl productService) {
        this.productService = productService;
    }

    public List<ProductDTO> getProductDTOList() {
        return convertEntityToDTO(productService.getProductList());
    }

    public List<ProductDTO> findProductsByEconomicCode(String economicCode) {
        return convertEntityToDTO(productService.findProductsByEconomicCode(economicCode));
    }

    private List<ProductDTO> convertEntityToDTO(List<Product> list) {
        return list.stream()
                .map(product ->
                        new ProductDTO(
                                product.getCode(),
                                product.getDescription(),
                                product.getEconomicCode()))
                .collect(Collectors.toList());
    }


}
