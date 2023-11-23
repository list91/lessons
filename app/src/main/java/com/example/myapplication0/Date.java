package com.example.myapplication0;

import java.util.Calendar;
import java.util.HashMap;

public class Date {

    public int thisDay;
    public int thisDayWeek;
    public int thisMonth;
    public int thisYear;
    public int hour;
    public int minute;
    public HashMap<Integer, String> daysWeek = new HashMap<>();
    public HashMap<Integer, String> monts = new HashMap<>();
    public Date(){
        // init Lists
        daysWeek.put(1, "понедельник");
        daysWeek.put(2, "вторник");
        daysWeek.put(3, "среда");
        daysWeek.put(4, "четверг");
        daysWeek.put(5, "пятница");
        daysWeek.put(6, "суббота");
        daysWeek.put(7, "воскресенье");

        monts.put(1, "января");
        monts.put(2, "февраля");
        monts.put(3, "марта");
        monts.put(4, "апреля");
        monts.put(5, "мая");
        monts.put(6, "июня");
        monts.put(7, "июля");
        monts.put(8, "августа");
        monts.put(9, "сентября");
        monts.put(10, "октября");
        monts.put(11, "ноября");
        monts.put(12, "декабря");

        Calendar calendar = Calendar.getInstance();


        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        thisDay = calendar.get(Calendar.DAY_OF_MONTH);
        thisMonth = calendar.get(Calendar.MONTH)+1;
        thisYear = calendar.get(Calendar.YEAR);
        thisDayWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
    }
    public String getThisStringRusMonth(){
        return monts.get(thisMonth);
    }
    public String getThisStringRusDayWeek(){
        return daysWeek.get(thisDayWeek);
    }

    public int getThisStartDayWeek(){
        return thisDay - (thisDayWeek-1);
    }

}
