package com.stepin.coronaapp.model;

import java.util.List;

public class SymptomsResponse {
    public String status;
    public String msg;
    public List<Data> data;

    public class Data {
        public int id;
        public String symptom;

        public int getId() {
            return id;
        }

        public String getSymptom() {
            return symptom;
        }
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public List<Data> getData() {
        return data;
    }
}
