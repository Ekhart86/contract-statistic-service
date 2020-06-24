package ru.ekhart86.contractservice.service.product;

import ru.ekhart86.contractservice.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getProductList();

    List<Product> findProductsByEconomicCode(String economicCode);
}
