package com.example.findhospital.view

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.NumberPicker
import com.example.findhospital.R

class timePicker(context: Context) {

    private val dlg = Dialog(context)
    private lateinit var btnOK : Button
    private lateinit var btnCancel : Button
    private lateinit var listener : MyDialogOKClickedListener

    fun start(content: String){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.time_dialog)
        dlg.setCancelable(true)

        var times = arrayOf("00", "30")

        var hourpick : NumberPicker = dlg.findViewById(R.id.HourPick)
        var minutepick : NumberPicker = dlg.findViewById(R.id.MinutePick)

        hourpick.minValue = 0
        hourpick.maxValue = 23

        minutepick.minValue = 0
        minutepick.maxValue = times.size - 1

        minutepick.displayedValues = times

        btnOK = dlg.findViewById(R.id.ok)
        btnCancel = dlg.findViewById(R.id.cancel)

        btnOK.setOnClickListener {
            var reTime = hourpick.value.toString() + ":" + times[minutepick.value]
            listener.onOKClicked(reTime)
            dlg.dismiss()
        }

        btnCancel.setOnClickListener {
            dlg.dismiss()
        }
        dlg.show()
    }

    fun setOnOKClickedListener(listener: (String) -> Unit){
        this.listener = object: MyDialogOKClickedListener{
            override fun onOKClicked(content: String){
                listener(content)
            }
        }
    }

    interface MyDialogOKClickedListener{
        fun onOKClicked(content: String)
    }



}