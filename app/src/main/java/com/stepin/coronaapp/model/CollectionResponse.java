package com.stepin.coronaapp.model;

import java.util.List;

public class CollectionResponse {
    public String status;
    public String msg;
    public List<Data> data;

    public class Data {
        public int id;
        public String collection_name;

        public int getId() {
            return id;
        }

        public String getCollection_name() {
            return collection_name;
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
