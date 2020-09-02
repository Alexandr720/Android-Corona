package com.stepin.coronaapp.model;

public class ReportLocal {
    public int id;
    public String userid;
    public String report_type;
    public String syptom;
    public String lan;
    public String lon;
    public String city;
    public String state;
    public String address;
    public String country;
    public String addition;
    public String created;
//    public String collection_id;

    public ReportLocal(
        int id,
        String luser_id,
        String lreport_type,
        String symptom,
        String latitude,
        String longitude,
        String lcity,
        String lstate,
        String laddress,
        String lcountry,
        String addition,
        String created_time
//        String collection_id
    ){
        this.id = id;
        this.userid = luser_id;
        this.report_type = lreport_type;
        this.syptom = symptom;
        this.lan = latitude;
        this.lon = longitude;
        this.city = lcity;
        this.state = lstate;
        this.address = laddress;
        this.country = lcountry;
        this.addition = addition;
        this.created = created_time;
//        this.collection_id = collection_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreated() {
        return created;
    }

    public String getState() {
        return state;
    }

    public String getLon() {
        return lon;
    }

    public String getLan() {
        return lan;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getAddition() {
        return addition;
    }

    public String getAddress() {
        return address;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setLan(String lan) {
        this.lan = lan;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public String getReport_type() {
        return report_type;
    }

    public String getSyptom() {
        return syptom;
    }

    public String getUserid() {
        return userid;
    }

    public void setReport_type(String report_type) {
        this.report_type = report_type;
    }

    public void setSyptom(String syptom) {
        this.syptom = syptom;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

//    public String getCollection_id() {
//        return collection_id;
//    }
}
