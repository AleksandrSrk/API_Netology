package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;

public class Utils {
    // 2.11 - создаем метод который возвращает нам ссылку
    public static String getURL(String nasaURL) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(nasaURL);
        ObjectMapper mapper = new ObjectMapper();

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            NASAAnswer answer = mapper.readValue(response.getEntity().getContent(), NASAAnswer.class);
            // Возвращаем ссылку
            return answer.url;

        } catch (IOException e) {
            System.out.println("Сервер НАСА недоступен");
        }
        return "";
    }
}
