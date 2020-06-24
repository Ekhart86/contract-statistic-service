package ru.ekhart86.contractservice.service.product;

import org.springframework.stereotype.Service;
import ru.ekhart86.contractservice.domain.ProductDTO;
import ru.ekhart86.contractservice.entity.Product;
import ru.ekhart86.contractservice.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductDTO> getProductList() {
        return convertEntityToDTO(productRepository.findAll());
    }

    @Override
    public List<ProductDTO> findProductsByEconomicCode(String economicCode) {
        return convertEntityToDTO(productRepository.findByEconomicCode(economicCode.toUpperCase()));
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
