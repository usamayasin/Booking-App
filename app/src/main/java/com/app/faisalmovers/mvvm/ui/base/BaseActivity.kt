package com.app.faisalmovers.mvvm.ui.base

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.app.faisalmovers.R
import com.app.faisalmovers.mvvm.utils.Utility
import java.util.*
import kotlin.concurrent.schedule

open class BaseActivity: AppCompatActivity() {
    lateinit  var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    public fun initializeBaseActivityViews(){
        dialog = Dialog(this)
        Utility.initializeValidationErrorsHashMap()
    }
    public fun setProgressbar(  showStatus:Boolean) {
        dialog?.setContentView(R.layout.progress_dialog)
        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(0)
        )
        dialog?.setCancelable(false)
        if (!showStatus) {
            Timer().schedule(1200) {
                dialog?.dismiss()
            }
        } else {
            dialog?.show()
        }
    }

}