package com.stepin.coronaapp.model;

import java.util.List;



public class EnforceListResponse {

    public String status;
    public List<Data> enforceList;

    public class Data {
        public int id;
        public int enforce_type;
        public String title;
        public String description;
        public String image;
        public String file_type;

        public Data() {
        }

        public String getFile_type() {
            return file_type;
        }

        public void setFile_type(String file_type) {
            this.file_type = file_type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setType(int type) {
            this.enforce_type = type;
        }

        public int getType() {
            return enforce_type;
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


    }

    public String getStatus() {
        return status;
    }

    public List<Data> getEnforceList() {
        return enforceList;
    }
}