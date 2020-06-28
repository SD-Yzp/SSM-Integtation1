package cn.sd.yz.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String Date2String(Date date,String patt){
        DateFormat df = new SimpleDateFormat(patt);
        String str = df.format(date);
        return str;
    }

    public static Date String2Date(String str,String patt) throws ParseException {
        DateFormat df = new SimpleDateFormat(patt);
        Date date = df.parse(str);
        return date;
    }


}
