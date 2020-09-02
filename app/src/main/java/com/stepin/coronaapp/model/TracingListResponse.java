package com.stepin.coronaapp.model;

import java.util.List;

public class TracingListResponse {
    public String status;
    public List<TracingListResponse.Data> tracingList;

    public class Data {

        public int id;
        public String passenger_name = "";
        public String unique_card_number = "";
        public String service_type = "";
        public String tel_number = "";
        public String vehicle_number = "";
        public String destination = "";
        public String tracing_title = "";
        public String publish_date = "";
        public String home_address = "";
        public Data() {
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setHome_address(String home_address) {
            this.home_address = home_address;
        }

        public void setPassenger_name(String passenger_name) {
            this.passenger_name = passenger_name;
        }

        public void setUnique_card_number(String unique_card_number) {
            this.unique_card_number = unique_card_number;
        }

        public void setService_type(String service_type) {
            this.service_type = service_type;
        }

        public void setTel_number(String tel_number) {
            this.tel_number = tel_number;
        }

        public void setVehicle_number(String vehicle_number) {
            this.vehicle_number = vehicle_number;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public void setPublish_date(String publish_date) {
            this.publish_date = publish_date;
        }

        public void setTracing_title(String tracing_title) {
            this.tracing_title = tracing_title;
        }

        public int getId() {
            return id;
        }

        public String getDestination() {
            return destination;
        }

        public String getPassenger_name() {
            return passenger_name;
        }

        public String getService_type() {
            return service_type;
        }

        public String getTel_number() {
            return tel_number;
        }

        public String getUnique_card_number() {
            return unique_card_number;
        }

        public String getVehicle_number() {
            return vehicle_number;
        }



        public String getPublish_date() {
            return publish_date;
        }

        public String getTracing_title() {
            return tracing_title;
        }



        public String getHome_address() {
            return home_address;
        }



    }
    public List<TracingListResponse.Data> getTracingList() {
        return tracingList;
    }
}
