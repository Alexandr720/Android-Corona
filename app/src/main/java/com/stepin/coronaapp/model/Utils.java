package com.stepin.coronaapp.model;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Patterns;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    static public String sharedPerfName = "CoronaPerf";
    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

    public boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @SuppressLint("SimpleDateFormat")
    public String getTime() {
        SimpleDateFormat format = new SimpleDateFormat("hh:mm a");
        return format.format(new Date());
    }

    public Date getDate (String str) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        try { return format.parse(str); }
        catch (ParseException e) { e.printStackTrace(); }
        return null;
    }

    public String getDateDiff(String format) {
        Date date = getDate(format);
        Date date1 = new Date();
        if (date == null) { return format; }

        long diff = date1.getTime() - date.getTime();
        long diffSeconds = diff / 1000 % 86400;
        long diffMinutes = diff / (60 * 1000) % 1440;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        System.out.println(diffHours);
        if (diffSeconds < 60) {
            return diffSeconds + " sec ago";
        } else if (diffMinutes < 60) {
            return diffMinutes + " min ago";
        } else if (diffHours < 24) {
            return diffHours + " hours ago";
        }
        return diffDays + "days ago";
    }

    public String capitalize(String str) {
        char ch[] = str.toCharArray();
        for (int i = 0; i < str.length(); i++) {

            if (i == 0 && ch[i] != ' ' ||
                    ch[i] != ' ' && ch[i - 1] == ' ') {
                if (ch[i] >= 'a' && ch[i] <= 'z') {
                    ch[i] = (char)(ch[i] - 'a' + 'A');
                }
            }

            else if (ch[i] >= 'A' && ch[i] <= 'Z')
                ch[i] = (char)(ch[i] + 'a' - 'A');
        }

        String st = new String(ch);
        return st;
    }
}
