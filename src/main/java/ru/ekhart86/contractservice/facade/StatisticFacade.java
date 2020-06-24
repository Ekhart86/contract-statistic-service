package ru.ekhart86.contractservice.facade;

import org.springframework.stereotype.Service;
import ru.ekhart86.contractservice.domain.ContractDTO;
import ru.ekhart86.contractservice.domain.SectorStatisticResponseDTO;
import ru.ekhart86.contractservice.entity.Product;
import ru.ekhart86.contractservice.service.contract.ContractServiceImpl;
import ru.ekhart86.contractservice.service.sector.SectorServiceImpl;
import ru.ekhart86.contractservice.service.product.ProductServiceImpl;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticFacade {

    private final ProductServiceImpl productService;
    private final ContractServiceImpl contractService;
    private final SectorServiceImpl economicSectorService;

    public StatisticFacade(ProductServiceImpl productService, ContractServiceImpl contractService, SectorServiceImpl economicSectorService) {
        this.productService = productService;
        this.contractService = contractService;
        this.economicSectorService = economicSectorService;
    }

    public SectorStatisticResponseDTO getStatisticByEconomicCode(Date startDate, Date endDate, String economicCode, String currencyCode) {
        var listProductCode = productService.findProductsByEconomicCode(economicCode).stream()
                .map(Product::getCode)
                .collect(Collectors.toList());
        List<ContractDTO> listContractDTO = listProductCode
                .stream()
                .map(productCode ->
                        contractService.findBySignDateAndProductCode(
                                startDate,
                                endDate,
                                productCode,
                                currencyCode))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        var quantityContracts = listContractDTO.size();
        var totalAmount = listContractDTO.stream().mapToLong(ContractDTO::getAmount).sum();
        var economicSectorDescription = economicSectorService.getEconomicSectorDescription(economicCode);
        return new SectorStatisticResponseDTO(economicCode, economicSectorDescription, startDate, endDate, quantityContracts, totalAmount, currencyCode);
    }

}
