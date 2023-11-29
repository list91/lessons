package com.example.myapplication0;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Objects;

public class ParserWeb {
    final private String startLinkStudents = "https://www.altspu.ru/schedule/students";
    final private String startLinkLecturers = "https://www.altspu.ru/schedule/lecturers";
    final private String startLinkRooms = "https://www.altspu.ru/schedule/rooms";
    private String startLinkObject;
    final private String mainDivContainerClassName = "p-0 p-xl-4";
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
    public void runStep(String link) throws IOException {
        Document document = Jsoup.connect("https://www.altspu.ru/schedule/lecturers/").get();
        System.out.println("@@@@@@@@@@");
        Element mainDiv = document.getElementsByClass(mainDivContainerClassName).get(0);

        getTypeDivList(mainDiv);

    }

    private String getTypeDivList(Element container){
        Element element = container.getElementsByClass("list-unstyled").get(0);
        return "element";
    }

}


