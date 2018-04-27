package com.techouts.pcomplaints.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressModel {

    @SerializedName("states")
    @Expose
    private List<State> states = null;
    @SerializedName("districts")
    @Expose
    private List<District> districts = null;
    @SerializedName("subDivisions")
    @Expose
    private List<SubDivision> subDivisions = null;
    @SerializedName("divisions")
    @Expose
    private List<DivisionPoliceStation> divisionPoliceStations = null;

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public List<SubDivision> getSubDivisions() {
        return subDivisions;
    }

    public void setSubDivisions(List<SubDivision> subDivisions) {
        this.subDivisions = subDivisions;
    }

    public List<DivisionPoliceStation> getDivisionPoliceStations() {
        return divisionPoliceStations;
    }

    public void setDivisionPoliceStations(List<DivisionPoliceStation> divisionPoliceStations) {
        this.divisionPoliceStations = divisionPoliceStations;
    }

    public static final class State {

        @SerializedName("stateCode")
        @Expose
        private String stateCode;
        @SerializedName("stateName")
        @Expose
        private String stateName;

        public String getStateCode() {
            return stateCode;
        }

        public void setStateCode(String stateCode) {
            this.stateCode = stateCode;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }
    }

    public static final class District {

        @SerializedName("districtCode")
        @Expose
        private String districtCode;
        @SerializedName("districtName")
        @Expose
        private String districtName;
        @SerializedName("stateCode")
        @Expose
        private String stateCode;

        public String getDistrictCode() {
            return districtCode;
        }

        public void setDistrictCode(String districtCode) {
            this.districtCode = districtCode;
        }

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public String getStateCode() {
            return stateCode;
        }

        public void setStateCode(String stateCode) {
            this.stateCode = stateCode;
        }
    }

    public static final class SubDivision {

        @SerializedName("subDivisionCode")
        @Expose
        private String subDivisionCode;
        @SerializedName("subDivisionName")
        @Expose
        private String subDivisionName;
        @SerializedName("districtCode")
        @Expose
        private String districtCode;

        public String getSubDivisionCode() {
            return subDivisionCode;
        }

        public void setSubDivisionCode(String subDivisionCode) {
            this.subDivisionCode = subDivisionCode;
        }

        public String getSubDivisionName() {
            return subDivisionName;
        }

        public void setSubDivisionName(String subDivisionName) {
            this.subDivisionName = subDivisionName;
        }

        public String getDistrictCode() {
            return districtCode;
        }

        public void setDistrictCode(String districtCode) {
            this.districtCode = districtCode;
        }
    }

    public static final class DivisionPoliceStation {
        @SerializedName("divisionCode")
        @Expose
        private String divisionCode;
        @SerializedName("divisionName")
        @Expose
        private String divisionName;
        @SerializedName("subDivisionCode")
        @Expose
        private String subDivisionCode;

        public String getDivisionCode() {
            return divisionCode;
        }

        public void setDivisionCode(String divisionCode) {
            this.divisionCode = divisionCode;
        }

        public String getDivisionName() {
            return divisionName;
        }

        public void setDivisionName(String divisionName) {
            this.divisionName = divisionName;
        }

        public String getSubDivisionCode() {
            return subDivisionCode;
        }

        public void setSubDivisionCode(String subDivisionCode) {
            this.subDivisionCode = subDivisionCode;
        }
    }
}
