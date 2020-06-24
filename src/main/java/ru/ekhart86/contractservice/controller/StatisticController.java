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
import ru.ekhart86.contractservice.facade.StatisticFacade;

import javax.validation.Valid;
import java.util.Arrays;

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
        var startDate = request.getStartDate();
        var endDate = request.getEndDate();
        var economicCode = request.getEconomicCode();
        var currencyCode = request.getCurrencyCode();
        statisticFacade.checkDateInDataBase(Arrays.asList(startDate, endDate));
        statisticFacade.startOlderThanEnd(startDate, endDate);
        statisticFacade.checkEconomicSector(economicCode);
        statisticFacade.checkCurrencyCode(currencyCode);
        return ResponseEntity.status(HttpStatus.OK).body(statisticFacade.getStatisticByEconomicCode(startDate, endDate, economicCode, currencyCode));
    }

}
