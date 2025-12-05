package com.nagesh.projectmanagement

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Locale

object Utils {
    fun dateFormatter(date: Any): LocalDate{
        val format = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        return LocalDate.parse(format.format(date))
    }

    fun dateToMillis(dateString: String?): Long {
        if (dateString.isNullOrEmpty()) return 0L
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = formatter.parse(dateString)
        return date?.time ?: 0L
    }

    fun showMessage(view: View, message: String, backgroundColor: Int, textColor: Int){
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackBar.setAction("Dismiss"){
            snackBar.dismiss()
        }
        snackBar.duration = 10000
        snackBar.setTextColor(textColor)
        snackBar.setBackgroundTint(backgroundColor)

        // Get the Snackbar's view
        val snackbarView = snackBar.view

// Change position to TOP
        val params = snackbarView.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        params.topMargin = 120  // optional padding from top
        snackbarView.layoutParams = params
        snackBar.show()
    }

}