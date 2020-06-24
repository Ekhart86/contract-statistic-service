package ru.ekhart86.contractservice.facade;

import org.springframework.stereotype.Service;
import ru.ekhart86.contractservice.domain.EconomicSectorDTO;
import ru.ekhart86.contractservice.exception.IllegalEconomicSectorException;
import ru.ekhart86.contractservice.service.sector.SectorServiceImpl;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class SectorFacade {

    private final SectorServiceImpl economicSectorService;

    public SectorFacade(SectorServiceImpl economicSectorService) {
        this.economicSectorService = economicSectorService;
    }

    public void checkEconomicSector(String sectorCode) {
        if (!economicSectorService.economicSectorExist(sectorCode.toUpperCase())) {
            throw new IllegalEconomicSectorException(sectorCode);
        }
    }

    public List<EconomicSectorDTO> getEconomicSectorDTOList() {
        return economicSectorService.getEconomicSectorList()
                .stream()
                .map(sector ->
                        new EconomicSectorDTO(
                                sector.getCode(),
                                sector.getDescription()))
                .collect(toList());
    }
}
