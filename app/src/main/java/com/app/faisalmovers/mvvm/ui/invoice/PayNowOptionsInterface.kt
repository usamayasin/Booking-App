package com.app.faisalmovers.mvvm.ui.invoice

import com.app.faisalmovers.mvvm.data.network.model.general.PayNowOptions

interface PayNowOptionsInterface {
    public fun onPayNowOptionClick(options: PayNowOptions)
}