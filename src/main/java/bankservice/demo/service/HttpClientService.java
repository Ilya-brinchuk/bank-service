package bankservice.demo.service;

import bankservice.demo.model.Currency;
import java.time.LocalDate;

public interface HttpClientService {
    double getRate(Currency from, Currency to, LocalDate date);
}
