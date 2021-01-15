package com.app.faisalmovers.mvvm.data.network.model;

public class CityListModel {
    int cityId;
    String CityName;

    public CityListModel(int cityId, String cityName) {
        this.cityId = cityId;
        CityName = cityName;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }
}
