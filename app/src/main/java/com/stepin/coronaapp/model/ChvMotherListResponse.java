package com.stepin.coronaapp.model;

import java.util.List;

public class ChvMotherListResponse {
    public String status;
    public List<ChvMotherListResponse.Data> chvMotherList;

    public class Data {
        public int id;
        public int user_id;
        public String title;
        public String name;
        public String nhif;
        public String age;
        public String mother_id;
        public String village;
        public String ward;
        public String tel_num;
        public String clinic;
        public String due_date;
        public String folic;
        public String mary;
        public String children;
        public String remark;

        public Data() {
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setWard(String ward) {
            this.ward = ward;
        }

        public String getWard() {
            return ward;
        }

        public void setVillage(String village) {
            this.village = village;
        }

        public String getVillage() {
            return village;
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


        public String getRemark() {
            return remark;
        }


        public String getStatus() {
            return status;
        }



        public void setRemark(String remark) {
            this.remark = remark;
        }

        public void setNhif(String nhif) {
            this.nhif = nhif;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNhif() {
            return nhif;
        }

        public String getName() {
            return name;
        }

        public String getChildren() {
            return children;
        }

        public String getClinic() {
            return clinic;
        }

        public String getDue_date() {
            return due_date;
        }

        public String getFolic() {
            return folic;
        }

        public String getMary() {
            return mary;
        }

        public String getMother_id() {
            return mother_id;
        }

        public String getTel_num() {
            return tel_num;
        }

        public void setChildren(String children) {
            this.children = children;
        }

        public void setClinic(String clinic) {
            this.clinic = clinic;
        }

        public void setDue_date(String due_date) {
            this.due_date = due_date;
        }

        public void setFolic(String folic) {
            this.folic = folic;
        }

        public void setMary(String mary) {
            this.mary = mary;
        }

        public void setMother_id(String mother_id) {
            this.mother_id = mother_id;
        }

        public void setTel_num(String tel_num) {
            this.tel_num = tel_num;
        }

    }


    public List<ChvMotherListResponse.Data> getChvMotherList() {
        return chvMotherList;
    }
}
