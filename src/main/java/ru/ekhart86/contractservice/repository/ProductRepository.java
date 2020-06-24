package ru.ekhart86.contractservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ekhart86.contractservice.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.code LIKE ?1% AND LENGTH(p.code) BETWEEN 2 AND 5")
    List<Product> getProductsByPartCode(String productCode);

    @Query("SELECT p FROM Product p WHERE LENGTH(p.code) BETWEEN 2 AND ?1")
    List<Product> getProductsBySymbolsLength(Integer symbols);

    @Override
    List<Product> findAll();

    List<Product> findByEconomicCode(String economicCode);



}
