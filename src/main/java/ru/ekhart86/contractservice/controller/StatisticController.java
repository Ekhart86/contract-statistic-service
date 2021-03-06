package ru.ekhart86.contractservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ekhart86.contractservice.domain.*;
import ru.ekhart86.contractservice.facade.StatisticFacade;

import javax.validation.Valid;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class StatisticController {

    private final StatisticFacade statisticFacade;

    public StatisticController(StatisticFacade statisticFacade) {
        this.statisticFacade = statisticFacade;
    }

    @PostMapping("/sector-statistic")
    public ResponseEntity<SectorStatisticResponseDTO> getSectorStatistic(@RequestBody @Valid SectorStatisticRequestDTO request) {
        Date startDate = request.getStartDate();
        Date endDate = request.getEndDate();
        String economicCode = request.getEconomicCode();
        String currencyCode = request.getCurrencyCode();
        statisticFacade.checkDateInDataBase(Arrays.asList(startDate, endDate));
        statisticFacade.startIsBeforeEnd(startDate, endDate);
        statisticFacade.periodIsValid(startDate, endDate);
        statisticFacade.checkEconomicSector(economicCode);
        statisticFacade.checkCurrencyCode(currencyCode);
        return ResponseEntity.status(HttpStatus.OK)
                .body(statisticFacade.getStatisticByEconomicCode(startDate, endDate, economicCode, currencyCode));
    }

    @PostMapping("/compare-economic-sector")
    public ResponseEntity<CompareSectorResponseDTO> compareEconomicSector(@RequestBody @Valid CompareSectorRequestDTO compareRequest) {
        String currencyCode = compareRequest.getCurrencyCode();
        String economicCode = compareRequest.getEconomicCode();
        Date startFromPeriod = compareRequest.getStartFromPeriod();
        Date endFromPeriod = compareRequest.getEndFromPeriod();
        Date startToPeriod = compareRequest.getStartToPeriod();
        Date endToPeriod = compareRequest.getEndToPeriod();
        statisticFacade.checkDateInDataBase(Arrays.asList(startFromPeriod, endFromPeriod, startToPeriod, endToPeriod));
        statisticFacade.startIsBeforeEnd(startFromPeriod, endFromPeriod);
        statisticFacade.periodIsValid(startFromPeriod, endFromPeriod);
        statisticFacade.startIsBeforeEnd(endFromPeriod, startToPeriod);
        statisticFacade.startIsBeforeEnd(startToPeriod, endToPeriod);
        statisticFacade.periodIsValid(startToPeriod, endToPeriod);
        statisticFacade.checkEconomicSector(economicCode);
        statisticFacade.checkCurrencyCode(currencyCode);
        return ResponseEntity.status(HttpStatus.OK)
                .body(statisticFacade.compareEconomicSector(startFromPeriod, endFromPeriod, startToPeriod, endToPeriod, economicCode, currencyCode));
    }

    @PostMapping("/compare-all-economic-sector")
    public ResponseEntity<List<CompareSectorResponseDTO>> compareAllEconomicSector(@RequestBody @Valid CompareAllSectorRequestDTO compareRequest) {
        String currencyCode = compareRequest.getCurrencyCode();
        Date startFromPeriod = compareRequest.getStartFromPeriod();
        Date endFromPeriod = compareRequest.getEndFromPeriod();
        Date startToPeriod = compareRequest.getStartToPeriod();
        Date endToPeriod = compareRequest.getEndToPeriod();
        statisticFacade.checkDateInDataBase(Arrays.asList(startFromPeriod, endFromPeriod, startToPeriod, endToPeriod));
        statisticFacade.periodIsValid(startFromPeriod, endFromPeriod);
        statisticFacade.startIsBeforeEnd(startFromPeriod, endFromPeriod);
        statisticFacade.startIsBeforeEnd(endFromPeriod, startToPeriod);
        statisticFacade.startIsBeforeEnd(startToPeriod, endToPeriod);
        statisticFacade.periodIsValid(startToPeriod, endToPeriod);
        statisticFacade.checkCurrencyCode(currencyCode);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(statisticFacade.getAllEconomicSectors()
                        .stream()
                        .map(sector -> statisticFacade.compareEconomicSector(
                                startFromPeriod,
                                endFromPeriod,
                                startToPeriod,
                                endToPeriod,
                                sector.getCode(),
                                currencyCode))
                        .collect(Collectors.toList()));
    }

}
