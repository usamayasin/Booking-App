package com.app.faisalmovers.mvvm.data.network.model.general

class PassengerList {

    var name:String?=""
    var cnic:String?=""
    var seatNo:String?=""
    var seatID:String?=""

    constructor(name: String?, cnic: String?, seatNo: String?, seatID: String?) {
        this.name = name
        this.cnic = cnic
        this.seatNo = seatNo
        this.seatID = seatID
    }
}