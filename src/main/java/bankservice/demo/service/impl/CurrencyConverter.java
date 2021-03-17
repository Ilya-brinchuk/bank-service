package bankservice.demo.service.impl;

import bankservice.demo.model.Currency;
import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CurrencyConverter {
    @Value(value = "${url.converter-api}")
    private String urlRequest;
    private final HttpClientService httpClient;

    public CurrencyConverter(HttpClientService httpClient) {
        this.httpClient = httpClient;
    }

    public BigDecimal getRate(Currency from, Currency to, LocalDate date) {
        String path = String.format("%s?from=%s&to=%s&date=%s",
                urlRequest, from.toString(), to.toString(), date);
        return new BigDecimal(httpClient.getResponse(path));
    }
}
