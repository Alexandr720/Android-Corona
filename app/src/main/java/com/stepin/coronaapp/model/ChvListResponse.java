package com.stepin.coronaapp.model;

import java.util.List;

public class ChvListResponse {
    public String status;
    public List<ChvListResponse.Data> chvList;

    public class Data {
        public int id;
        public int user_id;
        public String title;
        public String name;
        public String age;
        public String id_num;
        public String nhif;
        public String village;
        public String ward;
        public String nearname;
        public String mask;
        public String provide;
        public String mal;
        public String diabet;
        public String hyper;
        public String tb;
        public String indicate;
        public String remark;

        public Data() {
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getWard() {
            return ward;
        }

        public void setWard(String ward) {
            this.ward = ward;
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

        public void setId_num(String id_num) {
            this.id_num = id_num;
        }

        public String getId_num() {
            return id_num;
        }

        public String getDiabet() {
            return diabet;
        }

        public String getHyper() {
            return hyper;
        }

        public String getIndicate() {
            return indicate;
        }

        public String getMal() {
            return mal;
        }

        public String getMask() {
            return mask;
        }

        public String getName() {
            return name;
        }

        public String getNearname() {
            return nearname;
        }

        public String getNhif() {
            return nhif;
        }

        public String getProvide() {
            return provide;
        }

        public String getTb() {
            return tb;
        }

        public String getVillage() {
            return village;
        }

        public void setDiabet(String diabet) {
            this.diabet = diabet;
        }

        public void setHyper(String hyper) {
            this.hyper = hyper;
        }

        public void setIndicate(String indicate) {
            this.indicate = indicate;
        }

        public void setMal(String mal) {
            this.mal = mal;
        }

        public void setMask(String mask) {
            this.mask = mask;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setNearname(String nearname) {
            this.nearname = nearname;
        }

        public void setNhif(String nhif) {
            this.nhif = nhif;
        }

        public void setProvide(String provide) {
            this.provide = provide;
        }

        public void setTb(String tb) {
            this.tb = tb;
        }

        public void setVillage(String village) {
            this.village = village;
        }
    }


    public List<ChvListResponse.Data> getChvList() {
        return chvList;
    }
}
