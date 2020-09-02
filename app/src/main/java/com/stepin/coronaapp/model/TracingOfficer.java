package com.stepin.coronaapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TracingOfficer {
    public int id;
    public String created_time =  "";
    public String user_id_local = "";
    public String local_name = "";
    public String local_title = "";
    public String local_card_num = "";
    public String service_type = "";
    public String local_tel_num = "";
    public String local_home_address = "";
    public String local_reg_num = "";
    public String local_destination = "";
    public String local_date =  "";
    public TracingOfficer(int id, String user_id_local, String local_name, String local_title, String local_card_num, String service_type, String local_tel_num, String local_home_address , String local_reg_num, String local_destination, String local_date, String created_time){
        this.id = id;
        this.created_time = created_time;
        this.user_id_local = user_id_local;
        this.local_name = local_name;
        this.local_title = local_title;
        this.local_card_num = local_card_num;
        this.service_type = service_type;
        this.local_tel_num = local_tel_num;
        this.local_home_address = local_home_address;
        this.local_reg_num = local_reg_num;
        this.local_destination = local_destination;
        this.local_date = local_date;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getLocal_card_num() {
        return local_card_num;
    }

    public String getLocal_date() {
        return local_date;
    }

    public String getLocal_destination() {
        return local_destination;
    }

    public String getLocal_home_address() {
        return local_home_address;
    }

    public String getLocal_name() {
        return local_name;
    }

    public String getLocal_reg_num() {
        return local_reg_num;
    }

    public String getLocal_tel_num() {
        return local_tel_num;
    }

    public String getLocal_title() {
        return local_title;
    }

    public String getUser_id_local() {
        return user_id_local;
    }

    public void setLocal_card_num(String local_card_num) {
        this.local_card_num = local_card_num;
    }

    public void setLocal_date(String local_date) {
        this.local_date = local_date;
    }

    public void setLocal_destination(String local_destination) {
        this.local_destination = local_destination;
    }

    public void setLocal_home_address(String local_home_address) {
        this.local_home_address = local_home_address;
    }

    public void setLocal_name(String local_name) {
        this.local_name = local_name;
    }

    public void setLocal_reg_num(String local_reg_num) {
        this.local_reg_num = local_reg_num;
    }

    public void setLocal_tel_num(String local_tel_num) {
        this.local_tel_num = local_tel_num;
    }

    public void setLocal_title(String local_title) {
        this.local_title = local_title;
    }

    public void setUser_id_local(String user_id_local) {
        this.user_id_local = user_id_local;
    }
}
