package ru.ekhart86.contractservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ekhart86.contractservice.domain.EconomicSectorDTO;
import ru.ekhart86.contractservice.domain.ProductDTO;
import ru.ekhart86.contractservice.facade.StatisticFacade;
import ru.ekhart86.contractservice.service.product.ProductServiceImpl;
import ru.ekhart86.contractservice.service.sector.SectorServiceImpl;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class InfoController {

    private final SectorServiceImpl sectorService;
    private final ProductServiceImpl productService;
    private final StatisticFacade statisticFacade;

    public InfoController(SectorServiceImpl sectorService, ProductServiceImpl productService, StatisticFacade statisticFacade) {
        this.sectorService = sectorService;
        this.productService = productService;
        this.statisticFacade = statisticFacade;
    }

    @GetMapping("/economic-sectors")
    public ResponseEntity<List<EconomicSectorDTO>> getEconomicSectors() {
        return ResponseEntity.status(HttpStatus.OK).body(sectorService.getEconomicSectorList());
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductList());
    }

    @GetMapping("/products/{economicCode}")
    public ResponseEntity<List<ProductDTO>> getProducts(@PathVariable String economicCode) {
        statisticFacade.checkEconomicSector(economicCode);
        return ResponseEntity.status(HttpStatus.OK).body(productService.findProductsByEconomicCode(economicCode));
    }
}
