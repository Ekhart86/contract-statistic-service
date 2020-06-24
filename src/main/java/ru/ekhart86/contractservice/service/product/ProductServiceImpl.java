package ru.ekhart86.contractservice.service.product;

import org.springframework.stereotype.Service;
import ru.ekhart86.contractservice.entity.Product;
import ru.ekhart86.contractservice.repository.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findProductsByEconomicCode(String economicCode) {
        return productRepository.findByEconomicCode(economicCode);
    }
}
