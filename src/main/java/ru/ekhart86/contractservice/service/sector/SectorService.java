package ru.ekhart86.contractservice.service.sector;

import ru.ekhart86.contractservice.domain.EconomicSectorDTO;

import java.util.List;

public interface SectorService {
    List<EconomicSectorDTO> getEconomicSectorList();

    boolean economicSectorExist(String economicCode);

    String getEconomicSectorDescription(String economicCode);
}
