package com.stepin.coronaapp.model;

import androidx.annotation.Nullable;

import java.util.List;



public class GbvListResponse {

    public String status;
    public List<Data> gbvList;

    public class Data {
        public int id;
        public int user_id;
        public String title;
        public String county;
        public String gender;
        public String age;
        public String report_date;
        public String status;
        public String report_place;
        public String remark;
        public String officer_name;
        public String signature;
        public String nature;

        public Data() {
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
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

        public String getOfficer_name() {
            return officer_name;
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

        public String getSignature() {
            return signature;
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

        public void setOfficer_name(String officer_name) {
            this.officer_name = officer_name;
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

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }


    public List<Data> getGbvList() {
        return gbvList;
    }
}