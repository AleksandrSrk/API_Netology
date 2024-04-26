package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class MyTelegramBot extends TelegramLongPollingBot {
    // 2.1. Создали класс бота.
    // 2.2. Должны указать от какого класса он будет наследоваться/ Добавил в название класса extends TelegramLongPollingBot - прописал наследование от класса
    // TelegramLongPollingBot, из добавленной библиотеки

    // 2.3. Согласно документации нужно переопределить и создать еще три метода (по автоисправлению добавляются два, третий препод по памяти взял:
    //onUpdateReceived, getBotUsername и getBotToken

    // 2.4. ЗАводим поля для нашего класса ботнейм и боттокен
    private final String BOT_NAME; // создаются константами и закрвается к ним одступ по прайвед
    private final String BOT_TOKEN;

    // ПЕренесли сюда ссылку из маин + убрали дату
    private final String NASA_URL = "https://api.nasa.gov/planetary/apod?" +
            "api_key=UdsL1z6MImFCl9m6MOgJ2WMRUQpl1syJanR7azjR";

    // 2.5. Создаем конструктор
    public MyTelegramBot(String botName, String botToken) throws TelegramApiException {
        BOT_NAME = botName;
        BOT_TOKEN = botToken;

        // 2.6. СВязываем бота в телеге и этого из кода. Регистрируем бота.
        // Создаем объект botsApi + по автоисправлению добавляем сключения

        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);


    }

    @Override
    public void onUpdateReceived(Update update) { // 2.9. Это самый важный метод, принимает все что пишут пользователи

        // Добавляю проверку из документации:
        if (update.hasMessage() && update.getMessage().hasText()) { // Это проверка что нам прислали сообщение текстом. Не файлом
            long chatId = update.getMessage().getChatId(); // Запрашиваем ID чата, который нам написал, чтобы ему же отправить ответ
            String text = update.getMessage().getText(); // Запрашиваем текст который нам написал пользователь
            String[] separetedText = text.split(" ");
            //System.out.println(NASA_URL + "&date=" + separetedText[1]);
            System.out.println(text);

            switch (separetedText[0]) {

                case "/help":
                case "/помощь":
                    sendMessage("Я бот роскосмоса, введи /image или /картинка", chatId);
                    break;

                case "/image":
                case "/картинка":
                    String image = Utils.getURL(NASA_URL);
                    sendMessage(image, chatId);
                    break;

                case "/date":
                case "/дата":

                    image = Utils.getURL(NASA_URL + "&date=" + separetedText[1]);
                    sendMessage(image, chatId);
                    break;

                default:
                    sendMessage("Я тебя не понимаю", chatId);
            }


        }

    }

    void sendMessage(String msgText, long chatId) {
        // Отправляем сообщение в ответ пользователю
        SendMessage msg = new SendMessage();

        // Настраиваем сообщение:

        msg.setChatId(chatId); // УКазали кому отправляем сообщение
//            String image = null;
//            try {
//                image = Utils.getURL(NASA_URL);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        msg.setText(msgText); // Текст сообщения

        // Отравляем сообщение:

        try {
            execute(msg); // Подчеркивает. Должны добавить исключение. Через автоисправление добавляем трай кетч
        } catch (TelegramApiException e) {
            System.out.println("Какая то ошибка");
        }


    }

    @Override
    public String getBotUsername() {
        // 2.7. Добавляем что тут возвращаем имя бота
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        // 2.8. Добавляем что возвращаем токен
        return BOT_TOKEN;
    }

}

// 2.10. В мейне берем весь код почти, и перемещаем его во вновь созданный класс Utils
