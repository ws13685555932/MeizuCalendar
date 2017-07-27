package com.example.wangsheng.calendar;

import org.joda.time.DateTime;

/**
 * Created by wangsheng
 * on 2017/7/27.
 */

public class Utils {

    public static boolean isToday(DateTime dt1){
        DateTime dt2 = new DateTime();
        if(dt1.getYear() == dt2.getYear()
                && dt1.getMonthOfYear() == dt2.getMonthOfYear()
                && dt1.getDayOfMonth() == dt2.getDayOfMonth()){
            return true;
        }else{
            return false;
        }
    }
}
