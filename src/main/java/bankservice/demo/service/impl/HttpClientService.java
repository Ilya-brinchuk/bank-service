package bankservice.demo.service.impl;

import bankservice.demo.exception.DataProcessingException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.stereotype.Service;

@Service
public class HttpClientService {

    public String getResponse(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            JsonElement root = JsonParser
                    .parseReader(new InputStreamReader((InputStream) request.getContent()));
            JsonObject jsonObject = root.getAsJsonObject();

            return jsonObject.get("result").getAsString();
        } catch (IOException e) {
            throw new DataProcessingException("Can't get connection by this url: "
                    + path, e);
        }
    }
}
