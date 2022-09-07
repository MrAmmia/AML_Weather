package net.thebookofcode.www.amlweather.ui

import android.app.Activity
import android.app.AlertDialog
import net.thebookofcode.www.amlweather.R

class CustomWeatherDialog {
    private var activity: Activity? = null
    private var dialog: AlertDialog? = null

    constructor(activity: Activity?) {
        this.activity = activity
    }
    init {

    }

    fun startAlertDialog() {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        builder.setView(inflater.inflate(R.layout.search_popup_layout, null))
        builder.setCancelable(true)
        dialog = builder.create()
        dialog!!.show()
    }

}