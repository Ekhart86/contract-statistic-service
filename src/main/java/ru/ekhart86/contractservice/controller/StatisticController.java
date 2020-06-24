package ru.ekhart86.contractservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ekhart86.contractservice.domain.SectorStatisticRequestDTO;
import ru.ekhart86.contractservice.domain.SectorStatisticResponseDTO;
import ru.ekhart86.contractservice.facade.DateVerificationFacade;
import ru.ekhart86.contractservice.facade.SectorFacade;
import ru.ekhart86.contractservice.facade.StatisticFacade;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api")
public class StatisticController {

    private final StatisticFacade statisticFacade;
    private final SectorFacade sectorFacade;
    private final DateVerificationFacade dateVerificationFacade;

    public StatisticController(StatisticFacade statisticFacade, SectorFacade sectorFacade, DateVerificationFacade dateVerificationFacade) {
        this.statisticFacade = statisticFacade;
        this.sectorFacade = sectorFacade;
        this.dateVerificationFacade = dateVerificationFacade;
    }

    @PostMapping("/sector-statistic")
    public ResponseEntity<SectorStatisticResponseDTO> getSectorStatistic(@RequestBody @Valid SectorStatisticRequestDTO request) {
        var startDate = request.getStartDate();
        var endDate = request.getEndDate();
        var economicCode = request.getEconomicCode();
        var currencyCode = request.getCurrencyCode();
        dateVerificationFacade.checkDateInDataBase(startDate, endDate);
        dateVerificationFacade.startOlderThanEnd(startDate, endDate);
        sectorFacade.checkEconomicSector(economicCode);
        return ResponseEntity.status(HttpStatus.OK)
                .body(statisticFacade.getStatisticByEconomicCode(startDate, endDate, economicCode, currencyCode));
    }

}
