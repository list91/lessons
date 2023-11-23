package com.example.myapplication0;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class TableExcel {
    public static boolean checkStringContains(String mainString, String subString, boolean ignoreCase) {
        if (ignoreCase) {
            return mainString.toLowerCase().contains(subString.toLowerCase());
        } else {
            return mainString.contains(subString);
        }
    }


    public static Sheet getSheet() throws IOException {


        Document document = Jsoup.connect("https://old.altspu.ru/timetable/ifmo-tt/").get(); // Получение HTML-документа по URL-адресу

        Element generalDiv = document.getElementById("general");
        assert generalDiv != null;
        Elements elements = generalDiv.select("*.content"); // замените "class" на фактическое имя класса
//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        // если есть "ДО" и *текуще число - начало текущей недели* и *текущий месяц*

        Date date = new Date();
        int day = date.getThisStartDayWeek();
        String month = date.getThisStringRusMonth();
        String tableLink = null;
        for (Element element : elements) {
            String content = element.text();


            boolean metka0 = checkStringContains(content, "ДО", false);
            boolean metka1 = checkStringContains(content, Integer.toString(day), true);
            boolean metka2 = checkStringContains(content, month, true);


            if (metka0 && metka1 && metka2){
//                System.out.println(content);
                tableLink = Objects.requireNonNull(element.selectFirst("*.attachment")).select("a[href]").attr("href");

            }

        }

        // Перебор всех ссылок на странице и вывод в консоль
//        Elements links = document.select("a[href]");
//        for (Element link : links) {
//            String href = link.attr("href");
//            System.out.println(href);
//        }





        // Загрузка файла по ссылке
        URL url = new URL(tableLink);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        InputStream inputStream = connection.getInputStream();

// Создание книги Excel из потока
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // Получение первого листа

        // Закрытие ресурсов
        workbook.close();
        inputStream.close();
        connection.disconnect();
        return sheet;
    }
}
