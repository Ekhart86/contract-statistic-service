package ru.ekhart86.contractservice.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ekhart86.contractservice.entity.Product;

import java.util.List;

@Repository
public interface ProductDao extends CrudRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.code LIKE ?1% AND LENGTH(p.code) BETWEEN 2 AND 12")
    List<Product> getProductByPartCode(String productCode);

}
