package com.app.faisalmovers.mvvm.ui.home

import com.app.faisalmovers.mvvm.data.network.model.general.CityListModel

interface HomeActivityInterface {
    public fun getSelectedCity(cityName:CityListModel,type:String)
}