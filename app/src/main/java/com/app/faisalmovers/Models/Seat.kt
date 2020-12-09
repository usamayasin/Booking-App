package com.app.faisalmovers

class Seat(seatNumber: String?) {
    private var seatNumber: String
    init {
        this.seatNumber = seatNumber!!
    }
    fun getSeatNumber(): String? {
        return seatNumber
    }
    fun setSeatNumber(seatNumber: String?) {
        this.seatNumber = seatNumber!!
    }
}