package com.order.android.utils;

public interface Const {

    public static final String WEB_SERVICE="WEB_SERVICE";
    public static final String FACE_SERVICE="FACE_SERVICE";

    public static  String getWeekDay(int week){
        String weekDay="";
        switch (week){
            case 1:
                weekDay="星期日";
                break;
            case 2:
                weekDay="星期一";
                break;
            case 3:
                weekDay="星期二";
                break;
            case 4:
                weekDay="星期三";
                break;
            case 5:
                weekDay="星期四";
                break;
            case 6:
                weekDay="星期五";
                break;
            case 7:
                weekDay="星期六";
                break;

        }

        return weekDay;
    }
}
