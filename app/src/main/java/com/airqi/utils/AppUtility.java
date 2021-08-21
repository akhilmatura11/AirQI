package com.airqi.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AppUtility {

    public static String getFormattedTimestamp(Long timestamp){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);

        Calendar toCheck = Calendar.getInstance();
        String time =null;
        if(calendar.get(Calendar.DAY_OF_YEAR) == toCheck.get(Calendar.DAY_OF_YEAR)
                && calendar.get(Calendar.YEAR) ==  toCheck.get(Calendar.YEAR)){
            if(calendar.get(Calendar.HOUR_OF_DAY) == toCheck.get(Calendar.HOUR_OF_DAY)){
                if(calendar.get(Calendar.MINUTE) == toCheck.get(Calendar.MINUTE)){
                    time = "few seconds ago";
                }else{
                    int diff = toCheck.get(Calendar.MINUTE) - calendar.get(Calendar.MINUTE);
                    if(diff == 1)
                        time = diff + " minute ago";
                    else
                        time = diff + " minutes ago";
                }
            }else{
                int diff = toCheck.get(Calendar.HOUR_OF_DAY) - calendar.get(Calendar.HOUR_OF_DAY);
                if(diff == 1)
                    time = diff + " hour ago";
                else
                    time = diff + " hours ago";
            }
        }else if(isYesterday(calendar)){
            time = "updated yesterday";
        }else{
            time = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.getTime());
        }
        return time;
    }

    private static boolean isYesterday(Calendar calendar) {
        Calendar tooCheck = Calendar.getInstance();
        tooCheck.add(Calendar.DAY_OF_YEAR, -1);

        return calendar.get(Calendar.DAY_OF_YEAR) ==  tooCheck.get(Calendar.DAY_OF_YEAR)
                && calendar.get(Calendar.YEAR) == tooCheck.get(Calendar.YEAR);
    }

    public static String getTime(long timeStamp) {
        Calendar calendar =Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);

        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.getTime());
    }
}
