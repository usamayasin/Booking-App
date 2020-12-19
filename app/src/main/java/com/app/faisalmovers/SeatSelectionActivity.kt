package com.app.faisalmovers

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class SeatSelectionActivity : AppCompatActivity() {

    private var genderSelectionDialog: Dialog? = null
    private lateinit var seatsAdapter: SeatsAdapter
    private var ivGenderMale: ImageView? = null
    private var ivGenderFemale: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.seat_selection_activity)

        val seatSelectionGoButton: ImageView = findViewById(R.id.iv_seat_selection_go_button)
        seatSelectionGoButton.setOnClickListener {
            val intent = Intent(this@SeatSelectionActivity, PassengerDetailsActivity::class.java)
            startActivity(intent)
        }
    }

    fun selectSeat(view: View) {
        var selectedSeat: CardView? = null;
        selectedSeat = findViewById(view.id);
        controlSeatSelection(selectedSeat)
        //showGenderSelectionDialog(this@SeatSelectionActivity, selectedSeat)
    }

    fun controlSeatSelection(cardView: CardView) {
        if (cardView.cardBackgroundColor.defaultColor.equals(-1)) {
            cardView.setCardBackgroundColor(Color.GREEN)
        } else {
            cardView.setCardBackgroundColor(Color.WHITE)
        }
    }

    private fun showGenderSelectionDialog(context: Context, selectedSeat: CardView) {

        genderSelectionDialog = Dialog(context)
        genderSelectionDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        genderSelectionDialog?.setContentView(R.layout.gender_selection_dialog)
        genderSelectionDialog?.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        genderSelectionDialog?.show()

        ivGenderMale = genderSelectionDialog?.findViewById(R.id.iv_gender_male)
        ivGenderMale?.setOnClickListener {
            controlSeatSelection(selectedSeat, true)
        }

        ivGenderFemale = genderSelectionDialog?.findViewById(R.id.iv_gender_female)
        ivGenderFemale?.setOnClickListener {
            controlSeatSelection(selectedSeat, false)
        }
    }
    fun controlSeatSelection(cardView: CardView, gender: Boolean) {
        if (gender) {
            cardView.setCardBackgroundColor(resources.getColor(R.color.male_text_color))
        } else {
            cardView.setCardBackgroundColor(resources.getColor(R.color.female_text_color))
        }

        val firstChild: LinearLayout = cardView.getChildAt(0) as LinearLayout
        val tvChild: TextView = firstChild.getChildAt(0) as TextView
        tvChild.setTextColor(resources.getColor(R.color.white))
        genderSelectionDialog?.dismiss()
    }
}
