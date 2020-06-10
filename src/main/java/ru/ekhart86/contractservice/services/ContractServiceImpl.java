package ru.ekhart86.contractservice.services;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.ekhart86.contractservice.dto.ContractResponse;

@Service
@Data
@ConfigurationProperties(prefix = "api")
public class ContractServiceImpl implements ContractService {

    private RestTemplate restTemplate;
    private String url;

    public ContractServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ContractResponse findContractsByProduct(String productCode, String date) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(url + "/search/")
                .queryParam("okdp_okpd", productCode)
                .queryParam("daterange", date)
                .queryParam("sort", "-price");
        ContractResponse contractResponseByOkpd;
        try {
            contractResponseByOkpd = restTemplate.getForEntity(uriBuilder.toUriString(), ContractResponse.class).getBody();
            return contractResponseByOkpd;
        } catch (HttpClientErrorException ex) {
            if (ex.getStatusCode() != HttpStatus.NOT_FOUND) {
                throw ex;
            } else {
                return null;
            }
        }
    }
}
