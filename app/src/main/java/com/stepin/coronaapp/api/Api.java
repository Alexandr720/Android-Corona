package com.stepin.coronaapp.api;

import android.net.Uri;

import java.util.LinkedHashMap;
import java.util.Map;

public class Api {

    public static String baseUrl = "199.193.6.140";
    public static String login = "login";
    public static String register = "register";
    public static String forgot = "forgot_password";
    public static String update = "update_profile";
    public static String risk = "information_center_detail";
    public static String news = "getnews";
    public static String checkIn = "check_in";
    public static String report = "report";
    public static String report_all = "report_all_submit";
    public static String checkin_all = "check_in_all_submit";
    public static String enforce_all = "enforce_all_submit";

    public static String visit_all = "visit_all_submit";
    public static String mother_all = "mother_all_submit";
    public static String gbv_all = "gbv_all_submit";
    public static String office_all = "office_all_submit";
    public static String passenger_all = "passenger_all_submit";


    public static String enforce = "enforce";
    public static String tracing = "tracing";
    public static String tracing_submit = "tracing_submit";
    public static String tracing_passenger_submit = "tracing_passenger_submit";
    public static String gbv_submit = "gbv_submit";
    public static String chv_submit = "chv_submit";
    public static String chv_mother_submit = "chv_mother_submit";
    public static String msg = "all_msg";
    public static String symptoms = "all_symptoms";
    public static String sendMessage = "message";
    public static String reportImage = "image_video";
    public static String informationCenter = "information_center";
    public static String enforce_list = "enforce_list";
    public static String tracing_list = "tracing_list";
    public static String passenger_list = "passenger_list";
    public static String gbv_list = "gbv_list";
    public static String chv_list = "chv_list";
    public static String chv_mother_list = "chv_mother_list";
    public static String profileImage = "profile_image";
    public static String token = "update_token";
    public static String collection = "collection";
    public static String informationImageUrl = "http://199.193.6.140/information_center_icon/";
    public static String imageBaseUrl = "http://199.193.6.140/assets/images/news/";
    public static String profileImageUrl = "http://199.193.6.140/profile_image/";
    public static String enforceImageUrl = "http://199.193.6.140/report_image/";
    public static String email = "covid19@laikipia.go.ke";
    public static String number = "+254706031031";

    public static String facebook = "https://www.facebook.com/Virus-Tracker-App-100763371572599/";
    public static String googlePlus = "https://google.com/";
    public static String twitter = "https://twitter.com/virustrackerapp";
    public static String insta = "https://www.instagram.com/thevirustrackerapp/";

    public String generateUrl(String path, LinkedHashMap<String, String> prams) {

        Uri.Builder builder = new Uri.Builder()
                .scheme("http")
                .authority(baseUrl)
                .appendPath("api")
                .appendPath(path);

        for (Map.Entry<String, String> map : prams.entrySet()) {
            builder.appendQueryParameter(map.getKey(), map.getValue());
        }

        return builder.build().toString();

    }
}
