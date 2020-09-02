package com.stepin.coronaapp.model;

import java.util.List;

public class CheckData {
    public int id;
    public String people;
    public String time;
    public String util;
    public String addition;
    public String city;
    public String state;
    public String address;
    public String country;
    public String user_id;
    public String lon;
    public String lan;
    public String created_time;
    public CheckData(int id, String people, String time, String util, String addition, String city, String state, String address, String country, String user_id, String lat, String lon, String created_time) {
        this.id = id;
        this.people = people;
        this.time = time;
        this.util = util;
        this.addition = addition;
        this.city = city;
        this.state = state;
        this.address = address;
        this.country = country;
        this.user_id = user_id;
        this.lan = lat;
        this.lon = lon;
        this.created_time = created_time;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setUtil(String util) {
        this.util = util;
    }

    public String getUser_id() {
        return user_id;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

    public String getAddition() {
        return addition;
    }

    public String getCreated_time() {
        return created_time;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getLan() {
        return lan;
    }

    public String getLon() {
        return lon;
    }

    public String getPeople() {
        return people;
    }

    public String getState() {
        return state;
    }

    public String getUtil() {
        return util;
    }
}
