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
import ru.ekhart86.contractservice.facade.ProductFacade;
import ru.ekhart86.contractservice.facade.SectorFacade;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class InfoController {

    private final SectorFacade sectorFacade;
    private final ProductFacade productFacade;

    public InfoController(SectorFacade sectorFacade, ProductFacade productFacade) {
        this.sectorFacade = sectorFacade;
        this.productFacade = productFacade;
    }

    @GetMapping("/economic-sectors")
    public ResponseEntity<List<EconomicSectorDTO>> getEconomicSectors() {
        return ResponseEntity.status(HttpStatus.OK).body(sectorFacade.getEconomicSectorDTOList());
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productFacade.getProductDTOList());
    }

    @GetMapping("/products/{economicCode}")
    public ResponseEntity<List<ProductDTO>> getProducts(@PathVariable String economicCode) {
        sectorFacade.checkEconomicSector(economicCode);
        return ResponseEntity.status(HttpStatus.OK).body(productFacade.findProductsByEconomicCode(economicCode.toUpperCase()));
    }
}
