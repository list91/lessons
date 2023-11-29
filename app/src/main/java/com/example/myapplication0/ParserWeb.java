package com.example.myapplication0;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ParserWeb {
    final private String startLinkStudents = "https://www.altspu.ru/schedule/students";
    final private String startLinkLecturers = "https://www.altspu.ru/schedule/lecturers";
    final private String startLinkRooms = "https://www.altspu.ru/schedule/rooms";
    private String startLinkObject;
    final private String mainDivContainerClassName = ".p-0.p-xl-4";
    ParserWeb(String codeName) throws IOException {
        startLinkObject = codeName;
        runStep(startLinkLecturers);
    }

    private String getSelectedLink(){
        if (Objects.equals(startLinkObject, "stud")){
            return startLinkStudents;
        } else if (Objects.equals(startLinkObject, "lect")) {
            return startLinkLecturers;
        } else if (Objects.equals(startLinkObject, "room")) {
            return startLinkRooms;
        } else {
            return null;
        }
    }
    public void runProcess(){

    }
//    public void runProcessStudent() throws IOException {
//        Document document = Jsoup.connect(startLinkStudents).get();
//        Element mainDiv = document.getElementsByClass(mainDivContainerClassName).get(0);
//        Elements menuList = mainDiv.getElementsByTag("li");
////        вывожу тут всевозможные варианты выбора линков для перехода к следующему шагу
//    }
    private String getHtml(String link) throws IOException {
        // Создание клиента OkHttp
        OkHttpClient client = new OkHttpClient();

// Создание запроса
        Request request = new Request.Builder()
                .url(link)
                .header("Connection", "close")
                .build();

// Выполнение запроса и получение ответа
        Response response = client.newCall(request).execute();

        String html = null;
// Проверка успешности запроса
        if (response.isSuccessful()) {
            // Получение тела ответа в виде строки
            html = response.body().string();

            // Вывод HTML-кода страницы
            System.out.println(html);
        } else {

            // Обработка неуспешного запроса
            System.out.println("Ошибка: " + response.code());
        }

// Закрытие ресурсов
        response.body().close();
        return html;
    }
    private Elements findDivs(Element container, String className){
        try {
            return container.getElementsByClass(className);
        }catch (Exception e){
            System.out.println(1);
            return null;
        }
    }
    public void runStep(String link) throws IOException {

        Document document = Jsoup.parse(getHtml(startLinkStudents));
//        w.getElement
        Elements mainDivs = document.select(mainDivContainerClassName);
        Element mainDiv = document.getElementsByClass(mainDivContainerClassName).get(0);
        Element element = mainDiv.getElementsByClass("list-unstyled").get(0);
        System.out.println(document.getElementsByClass("list-unstyled").get(0));

//        getTypeDivList(mainDiv);

    }

    private String getTypeDivList(Element container){
        Element element = container.getElementsByClass("list-unstyled").get(0);
        return "element";
    }

}


