package com.app.faisalmovers.mvvm.data.network.model.general

class SelectedRouteInfo {

    var fromId: Int = -1
    var fromName: String = ""
    var toId: Int = -1
    var toName: String = ""
    var date: String = ""
    var route = Route()
    var selectedSeatsList = HashMap<String, String>()
}