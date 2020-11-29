

package com.antonpriyma.githubber.Util

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.snackbar.Snackbar


object AppUtils {

    fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {

            e.printStackTrace()
        }

    }


    fun showSnack(isConnected: Boolean, view: View, context: Context) {
        val message: String
        val color: Int
        if (isConnected) {
            message = "Good! Connected to Internet"
        } else {
            message = "Sorry! Not connected to internet"
        }
        val snackbar = Snackbar
            .make(view, message, Snackbar.LENGTH_LONG)
            .setTextColor(Color.RED)
        snackbar.show()
    }

}