package com.stepin.coronaapp.model;

public class ChvVisitLocal {
    public int id;
    public String user_id;
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
    public String created_time;

    public ChvVisitLocal(int id,String luser_id,String ltitle, String lname, String lage, String lid_num, String lnhif,
                         String lvillage, String lnearname, String lmask,String lward,String lprovide,String lmal, String ldiabet,
                         String lhyper,String ltb,String lindicate,String lremark,String created_time){
        this.id = id;
        this.user_id = luser_id;
        this.title = ltitle;
        this.name = lname;
        this.age = lage;
        this.id_num = lid_num;
        this.nhif = lnhif;
        this.village = lvillage;
        this.nearname = lnearname;
        this.mask = lmask;
        this.ward = lward;
        this.provide = lprovide;
        this.mal = lmal;
        this.diabet = ldiabet;
        this.hyper = lhyper;
        this.tb = ltb;
        this.indicate = lindicate;
        this.remark = lremark;
        this.created_time = created_time;



    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public String getRemark() {
        return remark;
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
