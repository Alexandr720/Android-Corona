package com.stepin.coronaapp.model;

import java.util.List;

public class InformationCenterRiskData {

    public String status;
    public String msg;
    public List<Data> informationCenter;

    public class Data {
        public int id;
        public int informationCenterId;
        public String isRisk;
        public String description;
        public String dateCreated;
        public String dateUpdated;

        public Data() {
        }

        public int getId() {
            return id;
        }

        public int getInformationCenterId() {
            return informationCenterId;
        }

        public String getIsRisk() {
            return isRisk;
        }

        public String getDescription() {
            return description;
        }

        public String getDateCreated() {
            return dateCreated;
        }

        public String getDateUpdated() {
            return dateUpdated;
        }
    }

    public String getStatus() {
        return status;
    }

    public List<Data> getRiskData() {
        return informationCenter;
    }

    public String getMsg() {
        return msg;
    }
}
