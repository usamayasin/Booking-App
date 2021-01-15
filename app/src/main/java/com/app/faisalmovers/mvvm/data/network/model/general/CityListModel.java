package com.app.faisalmovers.mvvm.data.network.model.general;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityListModel {

    @SerializedName("id")
    @Expose
    int id;

    @SerializedName("name")
    @Expose
    String name;

    @SerializedName("cityAbbr")
    @Expose
    String cityAbbr;

    @SerializedName("country")
    @Expose
    String country;

    @SerializedName("province")
    @Expose
    int province;

    @SerializedName("active")
    @Expose
    int active;

    @SerializedName("createdAt")
    @Expose
    String createdAt;

    @SerializedName("updatedAt")
    @Expose
    String updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCityAbbr() {
        return cityAbbr;
    }

    public void setCityAbbr(String cityAbbr) {
        this.cityAbbr = cityAbbr;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public CityListModel() { }

    public CityListModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public CityListModel(int id, String name, String cityAbbr, String country, int province, int active, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.cityAbbr = cityAbbr;
        this.country = country;
        this.province = province;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
