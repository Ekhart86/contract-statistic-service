package ru.ekhart86.contractservice.service.product;

import ru.ekhart86.contractservice.domain.ProductDTO;

import java.util.List;

public interface ProductService {
    List<ProductDTO> getProductList();

    List<ProductDTO> findProductsByEconomicCode(String economicCode);
}
