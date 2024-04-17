package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1. создали переменную куда сохранили ссылку

        String url = "https://api.nasa.gov/planetary/apod?api_key=UdsL1z6MImFCl9m6MOgJ2WMRUQpl1syJanR7azjR"; // меняем тут ссылку под наш апикей

        // 2. Создаем объект, который будет посылать запросы. Это HTTP клиент

        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 3 Создаем запрос. Смотрим данные в инструкции сайта наса/ Передаем в запрос ссылку с сайта наса, отредактировав ее.

        HttpGet httpGet = new HttpGet(url);

        // 4. Подаем запрос, исполняем его. Сервер пришлет ответ (респонз) на запрос. Нужно его куда то сохранить.

        CloseableHttpResponse response = httpClient.execute(httpGet); // Подчеркивается так как в теории возможна ошибка сервера
        // Чтобы убрать подчеркивание, добавляем исключения, нажав на подсказку иде (добавится в описание метода throws IOException)

        // 5. Смотрим что лежит в респонзе с помощью класса сканер.
/*
        Scanner scanner = new Scanner(response.getEntity().getContent()); // сканируем ответ респонзе -- получаем доступ через гетентити  - переводим в побитовый режим через гетконтент.
*/
        // 6. Выводим на экран то что сканер прочитал из респонза
/*
        System.out.println(scanner.nextLine());
*/
        // {"date":"2024-04-16","explanation":"The explosion is over, but the consequences continue.  About eleven thousand years ago, a star in the constellation of Vela could be seen to explode, creating a strange point of light briefly visible to humans living near the beginning of recorded history.  The outer layers of the star crashed into the interstellar medium, driving a shock wave that is still visible today.  The featured image captures some of that filamentary and gigantic shock in visible light. As gas flies away from the detonated star, it decays and reacts with the interstellar medium, producing light in many different colors and energy bands. Remaining at the center of the Vela Supernova Remnant is a pulsar, a star as dense as nuclear matter that spins around more than ten times in a single second.   Monday's Eclipse Imagery: Notable Submissions to APOD","hdurl":"https://apod.nasa.gov/apod/image/2404/VelaSnr_CTIO_3989.jpg","media_type":"image","service_version":"v1","title":"Filaments of the Vela Supernova Remnant","url":"https://apod.nasa.gov/apod/image/2404/VelaSnr_CTIO_960.jpg"}

        // 7 Создаем в проекте новый файл. Не класс а файл!!! В него копируем строчку ответа со сканера.
        // Там разбиваем строку на ключ: значение. Пример ключ: значение "date":"2024-04-16"

        // 8 ТЕперь мы хотим чтобы программа качала автоматом данные, в тч картинку. Гугл - java json. Находим на Хабре инфу про библиотеку Jackson
        // Идем в Мавен репозиторий библиотек и там ищем библиотеку джексон. Подключаем ее.

        // 9 Создаем объект который будет преобразовывать ответ Json в класс Java
        // !!!! Он это делал между Closeable и HTTPGet, я попробую тут.

        ObjectMapper mapper = new ObjectMapper();

        // Создаем класс NASAAnswer/ Копируем туда поля (ключи) из файла AnswerJson
        // В тм классе делаем конструктор. Затем в параметрах конструктора добавляем к каждому @JsonProperty ()

        // 10 Создаем запрос
        // Прежде чем запустить седующий запрос answer - комментируем сканер метод, так как если сканером воспользуемся, то данные уже не дойдут до меппера.
// и возникнет ошибка.
        NASAAnswer answer = mapper.readValue(response.getEntity().getContent(), NASAAnswer.class); // указали откуда берем данные и в какой тип преобразовываем

        // 12 Хтим вывести ссылку на экран

        System.out.println(answer.url);






    }
}