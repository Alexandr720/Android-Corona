package com.stepin.coronaapp.model;

import java.util.List;

public class GoogleGeocode {


    /**
     * plus_code : {"compound_code":"P27Q+MC New York, NY, USA","global_code":"87G8P27Q+MC"}
     * results : [{"address_components":[{"long_name":"279","short_name":"279","types":["street_number"]},{"long_name":"Bedford Avenue","short_name":"Bedford Ave","types":["route"]},{"long_name":"Williamsburg","short_name":"Williamsburg","types":["neighborhood","political"]},{"long_name":"Brooklyn","short_name":"Brooklyn","types":["political","sublocality","sublocality_level_1"]},{"long_name":"Kings County","short_name":"Kings County","types":["administrative_area_level_2","political"]},{"long_name":"New York","short_name":"NY","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]},{"long_name":"11211","short_name":"11211","types":["postal_code"]}],"formatted_address":"279 Bedford Ave, Brooklyn, NY 11211, USA","geometry":{"location":{"lat":40.71423350000001,"lng":-73.9613686},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":40.71558248029151,"lng":-73.9600196197085},"southwest":{"lat":40.71288451970851,"lng":-73.96271758029151}}},"place_id":"ChIJT2x8Q2BZwokRpBu2jUzX3dE","plus_code":{"compound_code":"P27Q+MF Brooklyn, New York, United States","global_code":"87G8P27Q+MF"},"types":["bakery","cafe","establishment","food","point_of_interest","store"]},{"address_components":[{"long_name":"277","short_name":"277","types":["street_number"]},{"long_name":"Bedford Avenue","short_name":"Bedford Ave","types":["route"]},{"long_name":"Williamsburg","short_name":"Williamsburg","types":["neighborhood","political"]},{"long_name":"Brooklyn","short_name":"Brooklyn","types":["political","sublocality","sublocality_level_1"]},{"long_name":"Kings County","short_name":"Kings County","types":["administrative_area_level_2","political"]},{"long_name":"New York","short_name":"NY","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]},{"long_name":"11211","short_name":"11211","types":["postal_code"]}],"formatted_address":"277 Bedford Ave, Brooklyn, NY 11211, USA","geometry":{"location":{"lat":40.7142205,"lng":-73.9612903},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":40.71556948029149,"lng":-73.95994131970849},"southwest":{"lat":40.7128715197085,"lng":-73.9626392802915}}},"place_id":"ChIJd8BlQ2BZwokRAFUEcm_qrcA","plus_code":{"compound_code":"P27Q+MF New York, United States","global_code":"87G8P27Q+MF"},"types":["street_address"]},{"address_components":[{"long_name":"279","short_name":"279","types":["street_number"]},{"long_name":"Bedford Avenue","short_name":"Bedford Ave","types":["route"]},{"long_name":"Williamsburg","short_name":"Williamsburg","types":["neighborhood","political"]},{"long_name":"Brooklyn","short_name":"Brooklyn","types":["political","sublocality","sublocality_level_1"]},{"long_name":"Kings County","short_name":"Kings County","types":["administrative_area_level_2","political"]},{"long_name":"New York","short_name":"NY","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]},{"long_name":"11211","short_name":"11211","types":["postal_code"]},{"long_name":"4203","short_name":"4203","types":["postal_code_suffix"]}],"formatted_address":"279 Bedford Ave, Brooklyn, NY 11211, USA","geometry":{"bounds":{"northeast":{"lat":40.7142654,"lng":-73.9612131},"southwest":{"lat":40.7141551,"lng":-73.96138479999999}},"location":{"lat":40.7142015,"lng":-73.96130769999999},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":40.7155592302915,"lng":-73.95994996970849},"southwest":{"lat":40.7128612697085,"lng":-73.9626479302915}}},"place_id":"ChIJRYYERGBZwokRAM4n1GlcYX4","types":["premise"]},{"address_components":[{"long_name":"279","short_name":"279","types":["street_number"]},{"long_name":"Bedford Avenue","short_name":"Bedford Ave","types":["route"]},{"long_name":"Williamsburg","short_name":"Williamsburg","types":["neighborhood","political"]},{"long_name":"Brooklyn","short_name":"Brooklyn","types":["political","sublocality","sublocality_level_1"]},{"long_name":"Kings County","short_name":"Kings County","types":["administrative_area_level_2","political"]},{"long_name":"New York","short_name":"NY","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]},{"long_name":"11211","short_name":"11211","types":["postal_code"]}],"formatted_address":"279 Bedford Ave, Brooklyn, NY 11211, USA","geometry":{"location":{"lat":40.7142545,"lng":-73.9614527},"location_type":"RANGE_INTERPOLATED","viewport":{"northeast":{"lat":40.7156034802915,"lng":-73.96010371970848},"southwest":{"lat":40.7129055197085,"lng":-73.9628016802915}}},"place_id":"EigyNzkgQmVkZm9yZCBBdmUsIEJyb29rbHluLCBOWSAxMTIxMSwgVVNBIhsSGQoUChIJ8ThWRGBZwokR3E1zUisk3LUQlwI","types":["street_address"]},{"address_components":[{"long_name":"291-275","short_name":"291-275","types":["street_number"]},{"long_name":"Bedford Avenue","short_name":"Bedford Ave","types":["route"]},{"long_name":"Williamsburg","short_name":"Williamsburg","types":["neighborhood","political"]},{"long_name":"Brooklyn","short_name":"Brooklyn","types":["political","sublocality","sublocality_level_1"]},{"long_name":"Kings County","short_name":"Kings County","types":["administrative_area_level_2","political"]},{"long_name":"New York","short_name":"NY","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]},{"long_name":"11211","short_name":"11211","types":["postal_code"]}],"formatted_address":"291-275 Bedford Ave, Brooklyn, NY 11211, USA","geometry":{"bounds":{"northeast":{"lat":40.7145065,"lng":-73.9612923},"southwest":{"lat":40.7139055,"lng":-73.96168349999999}},"location":{"lat":40.7142045,"lng":-73.9614845},"location_type":"GEOMETRIC_CENTER","viewport":{"northeast":{"lat":40.7155549802915,"lng":-73.96013891970848},"southwest":{"lat":40.7128570197085,"lng":-73.96283688029149}}},"place_id":"ChIJ8ThWRGBZwokR3E1zUisk3LU","types":["route"]},{"address_components":[{"long_name":"11211","short_name":"11211","types":["postal_code"]},{"long_name":"Brooklyn","short_name":"Brooklyn","types":["political","sublocality","sublocality_level_1"]},{"long_name":"New York","short_name":"New York","types":["locality","political"]},{"long_name":"New York","short_name":"NY","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]}],"formatted_address":"Brooklyn, NY 11211, USA","geometry":{"bounds":{"northeast":{"lat":40.7280089,"lng":-73.9207299},"southwest":{"lat":40.7008331,"lng":-73.9644697}},"location":{"lat":40.7093358,"lng":-73.9565551},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":40.7280089,"lng":-73.9207299},"southwest":{"lat":40.7008331,"lng":-73.9644697}}},"place_id":"ChIJvbEjlVdZwokR4KapM3WCFRw","types":["postal_code"]},{"address_components":[{"long_name":"Williamsburg","short_name":"Williamsburg","types":["neighborhood","political"]},{"long_name":"Brooklyn","short_name":"Brooklyn","types":["political","sublocality","sublocality_level_1"]},{"long_name":"Kings County","short_name":"Kings County","types":["administrative_area_level_2","political"]},{"long_name":"New York","short_name":"NY","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]}],"formatted_address":"Williamsburg, Brooklyn, NY, USA","geometry":{"bounds":{"northeast":{"lat":40.7251773,"lng":-73.936498},"southwest":{"lat":40.6979329,"lng":-73.96984499999999}},"location":{"lat":40.7081156,"lng":-73.9570696},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":40.7251773,"lng":-73.936498},"southwest":{"lat":40.6979329,"lng":-73.96984499999999}}},"place_id":"ChIJQSrBBv1bwokRbNfFHCnyeYI","types":["neighborhood","political"]},{"address_components":[{"long_name":"Brooklyn","short_name":"Brooklyn","types":["political","sublocality","sublocality_level_1"]},{"long_name":"Kings County","short_name":"Kings County","types":["administrative_area_level_2","political"]},{"long_name":"New York","short_name":"NY","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]}],"formatted_address":"Brooklyn, NY, USA","geometry":{"bounds":{"northeast":{"lat":40.739446,"lng":-73.8333651},"southwest":{"lat":40.551042,"lng":-74.05663}},"location":{"lat":40.6781784,"lng":-73.9441579},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":40.739446,"lng":-73.8333651},"southwest":{"lat":40.551042,"lng":-74.05663}}},"place_id":"ChIJCSF8lBZEwokRhngABHRcdoI","types":["political","sublocality","sublocality_level_1"]},{"address_components":[{"long_name":"Kings County","short_name":"Kings County","types":["administrative_area_level_2","political"]},{"long_name":"Brooklyn","short_name":"Brooklyn","types":["political","sublocality","sublocality_level_1"]},{"long_name":"New York","short_name":"NY","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]}],"formatted_address":"Kings County, Brooklyn, NY, USA","geometry":{"bounds":{"northeast":{"lat":40.739446,"lng":-73.8333651},"southwest":{"lat":40.551042,"lng":-74.05663}},"location":{"lat":40.6528762,"lng":-73.95949399999999},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":40.739446,"lng":-73.8333651},"southwest":{"lat":40.551042,"lng":-74.05663}}},"place_id":"ChIJOwE7_GTtwokRs75rhW4_I6M","types":["administrative_area_level_2","political"]},{"address_components":[{"long_name":"New York","short_name":"New York","types":["locality","political"]},{"long_name":"New York","short_name":"NY","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]}],"formatted_address":"New York, NY, USA","geometry":{"bounds":{"northeast":{"lat":40.9175771,"lng":-73.70027209999999},"southwest":{"lat":40.4773991,"lng":-74.25908989999999}},"location":{"lat":40.7127753,"lng":-74.0059728},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":40.9175771,"lng":-73.70027209999999},"southwest":{"lat":40.4773991,"lng":-74.25908989999999}}},"place_id":"ChIJOwg_06VPwokRYv534QaPC8g","types":["locality","political"]},{"address_components":[{"long_name":"Long Island","short_name":"Long Island","types":["establishment","natural_feature"]},{"long_name":"New York","short_name":"NY","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]}],"formatted_address":"Long Island, New York, USA","geometry":{"bounds":{"northeast":{"lat":41.1612401,"lng":-71.85620109999999},"southwest":{"lat":40.5429789,"lng":-74.0419497}},"location":{"lat":40.789142,"lng":-73.13496099999999},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":41.1612401,"lng":-71.85620109999999},"southwest":{"lat":40.5429789,"lng":-74.0419497}}},"place_id":"ChIJy6Xu4VRE6IkRGA2UhmH59x0","types":["establishment","natural_feature"]},{"address_components":[{"long_name":"New York","short_name":"NY","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]}],"formatted_address":"New York, USA","geometry":{"bounds":{"northeast":{"lat":45.015861,"lng":-71.777491},"southwest":{"lat":40.4773991,"lng":-79.7625901}},"location":{"lat":43.2994285,"lng":-74.21793260000001},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":45.015861,"lng":-71.777491},"southwest":{"lat":40.4773991,"lng":-79.7625901}}},"place_id":"ChIJqaUj8fBLzEwRZ5UY3sHGz90","types":["administrative_area_level_1","political"]},{"address_components":[{"long_name":"United States","short_name":"US","types":["country","political"]}],"formatted_address":"United States","geometry":{"bounds":{"northeast":{"lat":71.5388001,"lng":-66.885417},"southwest":{"lat":18.7763,"lng":170.5957}},"location":{"lat":37.09024,"lng":-95.712891},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":71.5388001,"lng":-66.885417},"southwest":{"lat":18.7763,"lng":170.5957}}},"place_id":"ChIJCzYy5IS16lQRQrfeQ5K5Oxw","types":["country","political"]}]
     * status : OK
     */

    private PlusCodeBean plus_code;
    private String status;
    private List<ResultsBean> results;

    public PlusCodeBean getPlus_code() {
        return plus_code;
    }

    public void setPlus_code(PlusCodeBean plus_code) {
        this.plus_code = plus_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class PlusCodeBean {
        /**
         * compound_code : P27Q+MC New York, NY, USA
         * global_code : 87G8P27Q+MC
         */

        private String compound_code;
        private String global_code;

        public String getCompound_code() {
            return compound_code;
        }

        public void setCompound_code(String compound_code) {
            this.compound_code = compound_code;
        }

        public String getGlobal_code() {
            return global_code;
        }

        public void setGlobal_code(String global_code) {
            this.global_code = global_code;
        }
    }

    public static class ResultsBean {
        /**
         * address_components : [{"long_name":"279","short_name":"279","types":["street_number"]},{"long_name":"Bedford Avenue","short_name":"Bedford Ave","types":["route"]},{"long_name":"Williamsburg","short_name":"Williamsburg","types":["neighborhood","political"]},{"long_name":"Brooklyn","short_name":"Brooklyn","types":["political","sublocality","sublocality_level_1"]},{"long_name":"Kings County","short_name":"Kings County","types":["administrative_area_level_2","political"]},{"long_name":"New York","short_name":"NY","types":["administrative_area_level_1","political"]},{"long_name":"United States","short_name":"US","types":["country","political"]},{"long_name":"11211","short_name":"11211","types":["postal_code"]}]
         * formatted_address : 279 Bedford Ave, Brooklyn, NY 11211, USA
         * geometry : {"location":{"lat":40.71423350000001,"lng":-73.9613686},"location_type":"ROOFTOP","viewport":{"northeast":{"lat":40.71558248029151,"lng":-73.9600196197085},"southwest":{"lat":40.71288451970851,"lng":-73.96271758029151}}}
         * place_id : ChIJT2x8Q2BZwokRpBu2jUzX3dE
         * plus_code : {"compound_code":"P27Q+MF Brooklyn, New York, United States","global_code":"87G8P27Q+MF"}
         * types : ["bakery","cafe","establishment","food","point_of_interest","store"]
         */

        private String formatted_address;
        private GeometryBean geometry;
        private String place_id;
        private PlusCodeBeanX plus_code;
        private List<AddressComponentsBean> address_components;
        private List<String> types;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public GeometryBean getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryBean geometry) {
            this.geometry = geometry;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public PlusCodeBeanX getPlus_code() {
            return plus_code;
        }

        public void setPlus_code(PlusCodeBeanX plus_code) {
            this.plus_code = plus_code;
        }

        public List<AddressComponentsBean> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<AddressComponentsBean> address_components) {
            this.address_components = address_components;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public static class GeometryBean {
            /**
             * location : {"lat":40.71423350000001,"lng":-73.9613686}
             * location_type : ROOFTOP
             * viewport : {"northeast":{"lat":40.71558248029151,"lng":-73.9600196197085},"southwest":{"lat":40.71288451970851,"lng":-73.96271758029151}}
             */

            private LocationBean location;
            private String location_type;
            private ViewportBean viewport;

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getLocation_type() {
                return location_type;
            }

            public void setLocation_type(String location_type) {
                this.location_type = location_type;
            }

            public ViewportBean getViewport() {
                return viewport;
            }

            public void setViewport(ViewportBean viewport) {
                this.viewport = viewport;
            }

            public static class LocationBean {
                /**
                 * lat : 40.71423350000001
                 * lng : -73.9613686
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class ViewportBean {
                /**
                 * northeast : {"lat":40.71558248029151,"lng":-73.9600196197085}
                 * southwest : {"lat":40.71288451970851,"lng":-73.96271758029151}
                 */

                private NortheastBean northeast;
                private SouthwestBean southwest;

                public NortheastBean getNortheast() {
                    return northeast;
                }

                public void setNortheast(NortheastBean northeast) {
                    this.northeast = northeast;
                }

                public SouthwestBean getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(SouthwestBean southwest) {
                    this.southwest = southwest;
                }

                public static class NortheastBean {
                    /**
                     * lat : 40.71558248029151
                     * lng : -73.9600196197085
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class SouthwestBean {
                    /**
                     * lat : 40.71288451970851
                     * lng : -73.96271758029151
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }

        public static class PlusCodeBeanX {
            /**
             * compound_code : P27Q+MF Brooklyn, New York, United States
             * global_code : 87G8P27Q+MF
             */

            private String compound_code;
            private String global_code;

            public String getCompound_code() {
                return compound_code;
            }

            public void setCompound_code(String compound_code) {
                this.compound_code = compound_code;
            }

            public String getGlobal_code() {
                return global_code;
            }

            public void setGlobal_code(String global_code) {
                this.global_code = global_code;
            }
        }

        public static class AddressComponentsBean {
            /**
             * long_name : 279
             * short_name : 279
             * types : ["street_number"]
             */

            private String long_name;
            private String short_name;
            private List<String> types;

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }
        }
    }
}
