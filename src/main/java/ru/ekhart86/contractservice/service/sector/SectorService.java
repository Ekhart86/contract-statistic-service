package ru.ekhart86.contractservice.service.sector;

import ru.ekhart86.contractservice.domain.EconomicSectorDTO;
import ru.ekhart86.contractservice.entity.EconomicSector;

import java.util.List;

public interface SectorService {
    List<EconomicSector> getEconomicSectorList();
    boolean economicSectorExist(String economicCode);
    String getEconomicSectorDescription(String economicCode);
}
