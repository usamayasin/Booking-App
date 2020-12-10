package com.app.faisalmovers.Models

class RouteSelection {

     var busType:String=""
    var seatsLeft:String=""
    var price:Double=0.0
    var depature=""
    var arrival=""

    constructor(
        busType: String,
        seatsLeft: String,
        price: Double,
        depature: String,
        arrival: String
    ) {
        this.busType = busType
        this.seatsLeft = seatsLeft
        this.price = price
        this.depature = depature
        this.arrival = arrival
    }
}