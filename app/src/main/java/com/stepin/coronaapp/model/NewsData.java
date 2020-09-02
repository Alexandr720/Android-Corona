package com.stepin.coronaapp.model;

import java.util.List;

public class NewsData {
    public String status;
    public String msg;
    public List<Data> data;

    public NewsData() {
    }

    public class Data {
          public int id;
          public String title;
          public String description;
          public String url;
          public String createdAt;
          public String updatedAt;
          public String image;
          public String fileType;
          public String period;

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

        public String getUrl() {
            return url;
        }

        public String getDateCreated() {
            return createdAt;
        }

        public String getDateUpdated() {
            return updatedAt;
        }

        public String getImage() {
            return image;
        }

        public String getFileType() {
            return fileType;
        }

        public String getPeriod() {
            return period;
        }
    }

    public String getStatus() {
        return status;
    }

    public List<Data> getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }
}
