package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;

import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // 1. создали переменную куда сохранили ссылку

        String url = "https://api.nasa.gov/planetary/apod?" +
                "api_key=UdsL1z6MImFCl9m6MOgJ2WMRUQpl1syJanR7azjR" +
                "&date=2022-04-11"; // меняем тут ссылку под наш апикей

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

        // 6. Выводим на экран то что сканер прочитал из респонза

        System.out.println(scanner.nextLine());
*/
        // {"date":"2024-04-16","explanation":"The explosion is over, but the consequences continue.  About eleven thousand years ago, a star in the constellation of Vela could be seen to explode, creating a strange point of light briefly visible to humans living near the beginning of recorded history.  The outer layers of the star crashed into the interstellar medium, driving a shock wave that is still visible today.  The featured image captures some of that filamentary and gigantic shock in visible light. As gas flies away from the detonated star, it decays and reacts with the interstellar medium, producing light in many different colors and energy bands. Remaining at the center of the Vela Supernova Remnant is a pulsar, a star as dense as nuclear matter that spins around more than ten times in a single second.   Monday's Eclipse Imagery: Notable Submissions to APOD","hdurl":"https://apod.nasa.gov/apod/image/2404/VelaSnr_CTIO_3989.jpg","media_type":"image","service_version":"v1","title":"Filaments of the Vela Supernova Remnant","url":"https://apod.nasa.gov/apod/image/2404/VelaSnr_CTIO_960.jpg"}

        // 7 Создаем в проекте новый файл. Не класс а файл!!! В него копируем строчку ответа со сканера.
        // Там разбиваем строку на ключ: значение. Пример ключ: значение "date":"2024-04-16" (ктрл + альт + эл)

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
        // Тест гит

        System.out.println(answer.url); // выводится ссылка на картинку https://apod.nasa.gov/apod/image/2404/NGC1232_Eye_of_God_Galaxy_fullsize_2024-03-28_1024.jpg

//19 !!! Продолжение. Решаем проблему названия файла. Разбиваем ссылку в массив чере сплит
        String[] urlSeparated = answer.url.split("/");
        String fileName = "C:/Users/User-PRG/IdeaProjects/Nasa/DownloadImages/" + urlSeparated[urlSeparated.length-1]; // Добавляем путь к папке куда сохранять будем.
        // Проверить как на другом компе будет работать. Создастся новая папка???




        // 13 Хотим скачать картинку по ссылке. Снова создаем запрос на скачивание""

        HttpGet httpGetImage = new HttpGet(answer.url);

        // 14 Посылаем запрос

        CloseableHttpResponse image = httpClient.execute(httpGetImage);

        // 15 Создаем метод для сохранения файла из потока

        // FileOutputStream fileOutputStream = new FileOutputStream("Image.jpg"); - пределали когда решали вопрос с названием

        FileOutputStream fileOutputStream = new FileOutputStream(fileName);

        // 16 Обращаемся к ответу от сервера, получаем доступ к телу ответа, и записываем указывая куда:

        image.getEntity().writeTo(fileOutputStream); // fileOutputStream - сюда сохраняется картинка

        // 17 ТЕперь можем добавить параметры запроса, из инструкции с сайта наса.
        // Был добавлен только один обязательный - API KEY, сюда String url = "https://api.nasa.gov/planetary/apod?api_key=UdsL1z6MImFCl9m6MOgJ2WMRUQpl1syJanR7azjR";
        // Мы можем редактировать запрос. Добаваим в ссылку запрос - дату. Строчку можно разбивать.
        // date=YYYY-MM-DD" - добавил. А, надо вставлять реальную дату. date=2024-04-18 не забыть добавить амперсанд!!! &

        // 18 Решаем проблему что картинка следующая затирает предыдущую, сохраняем каждую картинку с отдельным наименованием.
        // Выше создадим массив, разобьем входящий УРЛ на части и возьем последнюю часть








    }


}