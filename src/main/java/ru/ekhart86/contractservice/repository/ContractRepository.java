package ru.ekhart86.contractservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ekhart86.contractservice.entity.Contract;

import java.sql.Date;
import java.util.List;

@Repository
public interface ContractRepository extends CrudRepository<Contract, Long> {

    @Query("SELECT c FROM Contract c WHERE c.productCode = ?3 AND c.currencyCode = ?4 AND c.signDate BETWEEN ?1 AND ?2")
    List<Contract> findBySignDateAndProductCode(Date startDate, Date endDate, String productCode, String currencyCode);
    @Query("SELECT c FROM Contract c WHERE c.productCode IN ?3 AND c.currencyCode = ?4 AND c.signDate BETWEEN ?1 AND ?2")
    List<Contract> findBySignDateAndListOfProductCode(Date startDate, Date endDate, List<String> productCode, String currencyCode);
    List<Contract> findBySignDate(Date date);
    List<Contract> findByCurrencyCode(String code);
}
