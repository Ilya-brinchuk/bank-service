package bankservice.demo.service.impl;

import bankservice.demo.exception.DataProcessingException;
import bankservice.demo.model.Currency;
import bankservice.demo.service.HttpClientService;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HttpClientServiceImpl implements HttpClientService {
    @Value(value = "${url.converter-api}")
    private String urlRequest;

    public double getRate(Currency from, Currency to, LocalDate date) {
        System.out.println(date);
        String path = urlRequest + "?from=" + from.toString()
                + "&to=" + to.toString() + "&date=" + date;
        try {
            URL url = new URL(path);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonElement root = JsonParser
                    .parseReader(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonObject = root.getAsJsonObject();

            return jsonObject.get("result").getAsNumber().doubleValue();
        } catch (IOException e) {
            throw new DataProcessingException("Can't get connection by this url: "
                    + path, e);
        }
    }
}
