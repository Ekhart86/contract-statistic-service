package ru.ekhart86.contractservice.service.sector;

import org.springframework.stereotype.Service;
import ru.ekhart86.contractservice.entity.EconomicSector;
import ru.ekhart86.contractservice.repository.SectorRepository;

import java.util.List;

@Service
public class SectorServiceImpl implements SectorService {

    private final SectorRepository sectorRepository;

    public SectorServiceImpl(SectorRepository sectorRepository) {
        this.sectorRepository = sectorRepository;
    }

    @Override
    public List<EconomicSector> getEconomicSectorList() {
        return sectorRepository.findAll();
    }

    @Override
    public boolean economicSectorExist(String economicCode) {
        return sectorRepository.findByCode(economicCode).size() > 0;
    }

    @Override
    public String getEconomicSectorDescription(String economicCode) {
        return sectorRepository.findByCode(economicCode).get(0).getDescription();
    }

}
