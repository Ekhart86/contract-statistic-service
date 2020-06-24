package ru.ekhart86.contractservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.ekhart86.contractservice.entity.EconomicSector;

import java.util.List;

@Repository
public interface SectorRepository extends CrudRepository<EconomicSector, Long> {

    @Override
    List<EconomicSector> findAll();

    List<EconomicSector> findByCode(String economicCode);

}
