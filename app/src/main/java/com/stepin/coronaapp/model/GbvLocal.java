package com.stepin.coronaapp.model;

public class GbvLocal {
    public int id;
    public String  user_id;
    public String title;
    public String county;
    public String gender;
    public String age;
    public String report_date;
    public String status;
    public String report_place;
    public String remark;
    public String nature;
    public String created;

    public GbvLocal(int id,String user_id, String title, String county, String gender, String age, String report_date, String status, String report_place, String remark , String nature, String created){
        this.id = id;
        this.title = title;
        this.user_id = user_id;
        this.county = county;
        this.gender = gender;
        this.age = age;
        this.report_date = report_date;
        this.status = status;
        this.report_place = report_place;
        this.remark = remark;
        this.created = created;
        this.nature = nature;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCounty() {
        return county;
    }

    public String getGender() {
        return gender;
    }


    public String getRemark() {
        return remark;
    }

    public String getReport_date() {
        return report_date;
    }

    public String getReport_place() {
        return report_place;
    }


    public String getStatus() {
        return status;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public void setReport_place(String report_place) {
        this.report_place = report_place;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreated() {
        return created;
    }
}
