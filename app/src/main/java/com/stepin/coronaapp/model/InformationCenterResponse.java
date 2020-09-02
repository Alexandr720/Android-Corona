package com.stepin.coronaapp.model;

import java.util.List;

public class InformationCenterResponse {

    public String status;
    public List<Data> informationCenter;

    public class Data {
        public int id;
        public String title;
        public String description;
        public String image;
        public String icon;
        public String content;

        public Data() {
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getImage() {
            return image;
        }

        public String getIcon() {
            return icon;
        }

        public String getContent() {
            return content;
        }
    }

    public String getStatus() {
        return status;
    }

    public List<Data> getInformationCenter() {
        return informationCenter;
    }
}
