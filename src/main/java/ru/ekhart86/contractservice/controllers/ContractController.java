package ru.ekhart86.contractservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ekhart86.contractservice.enums.ErrorMessage;
import ru.ekhart86.contractservice.fasades.CompareFacade;
import ru.ekhart86.contractservice.model.response.ComparisonResponse;

@RestController
public class ContractController {

    private CompareFacade compareFacade;

    public ContractController(CompareFacade compareFacade) {
        this.compareFacade = compareFacade;
    }

    @GetMapping("/compare")
    public ResponseEntity<Object> compareContracts(String productCode, String fromPeriod, String toPeriod) {
        if (compareFacade.checkDate(fromPeriod, toPeriod).equals(ErrorMessage.INCORRECT_DATE)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Некорректно указана дата, каждый диапазон дат необходимо указывать в формате dd.MM.yyyy-dd.MM.yyyy");
        }
        if (!compareFacade.checkProductCode(productCode)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Не найден указанный код продукт " + productCode);
        }
        ComparisonResponse response = compareFacade.compareByProduct(productCode, fromPeriod, toPeriod);
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Нет данных о контрактах для указанного продукта!");
        }
    }
}
