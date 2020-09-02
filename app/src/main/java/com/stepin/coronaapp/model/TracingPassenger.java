package com.stepin.coronaapp.model;

public class TracingPassenger {
    public int id;
    public String user_id;
    public String temp = "";
    public String passenger_name = "";
    public String tracing_title = "";
    public String vehicle_num = "";
    public String tel_number = "";
    public String seat_num = "";
    public String publish_date = "";
    public String from_village = "";
    public String to_village = "";
    public String contact = "";
    public String contact_num = "";
    public String location = "";
    public String infect_str = "";
    public String history_last = "";
    public String id_num = "";
    public String created = "";

    public TracingPassenger(int id, String user_id,String temp, String passenger_name, String tracing_title, String vehicle_num,
                            String tel_number, String seat_num, String publish_date, String from_village, String to_village,
                            String contact, String contact_num, String location, String infect_str, String history_last, String id_num,
                            String created) {
        this.id = id;
        this.user_id = user_id;
        this.passenger_name = passenger_name;
        this.tracing_title = tracing_title;
        this.vehicle_num = vehicle_num;
        this.tel_number = tel_number;
        this.temp = temp;
        this.seat_num = seat_num;
        this.publish_date = publish_date;
        this.from_village = from_village;
        this.to_village = to_village;
        this.contact = contact;
        this.contact_num = contact_num;
        this.location = location;
        this.infect_str = infect_str;
        this.history_last = history_last;
        this.id_num = id_num;
        this.created = created;

    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getId_num() {
        return id_num;
    }

    public void setId_num(String id_num) {
        this.id_num = id_num;
    }

    public String getInfect_str() {
        return infect_str;
    }

    public void setInfect_str(String infect_str) {
        this.infect_str = infect_str;
    }




    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getTemp() {
        return temp;
    }

    public String getHistory_last() {
        return history_last;
    }

    public void setHistory_last(String history_last) {
        this.history_last = history_last;
    }



    public void setId(int id) {
        this.id = id;
    }

    public String getFrom_village() {
        return from_village;
    }

    public String getLocation() {
        return location;
    }


    public String getSeat_num() {
        return seat_num;
    }


    public String getTo_village() {
        return to_village;
    }


    public String getVehicle_num() {
        return vehicle_num;
    }

    public void setFrom_village(String from_village) {
        this.from_village = from_village;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public void setSeat_num(String seat_num) {
        this.seat_num = seat_num;
    }


    public void setTo_village(String to_village) {
        this.to_village = to_village;
    }


    public void setVehicle_num(String vehicle_num) {
        this.vehicle_num = vehicle_num;
    }

    public void setPassenger_name(String passenger_name) {
        this.passenger_name = passenger_name;
    }



    public void setTel_number(String tel_number) {
        this.tel_number = tel_number;
    }



    public void setPublish_date(String publish_date) {
        this.publish_date = publish_date;
    }

    public void setTracing_title(String tracing_title) {
        this.tracing_title = tracing_title;
    }

    public int getId() {
        return id;
    }


    public String getPassenger_name() {
        return passenger_name;
    }


    public String getTel_number() {
        return tel_number;
    }





    public String getPublish_date() {
        return publish_date;
    }

    public String getTracing_title() {
        return tracing_title;
    }

    public String getContact() {
        return contact;
    }

    public String getContact_num() {
        return contact_num;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setContact_num(String contact_num) {
        this.contact_num = contact_num;
    }
}
