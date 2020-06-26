package ru.ekhart86.contractservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ekhart86.contractservice.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Override
    List<Product> findAll();
    List<Product> findByEconomicCode(String economicCode);
}
