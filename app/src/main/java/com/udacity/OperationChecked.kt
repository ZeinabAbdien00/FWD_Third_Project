package com.udacity

import android.annotation.SuppressLint
import android.widget.Toast
import com.udacity.constant.Constant
import com.udacity.ui.MainActivity

 var optionLink: String = ""
@SuppressLint("StaticFieldLeak")
//var main: MainActivity = TODO()

fun operationChecked(view: Int) {

    if (view != 0) {

        if (view == R.id.radio_glide) {
            optionLink = Constant.GLIDE_URL
            Constant.SELECTEDFILENAME = "GLIDE DOWNLOADED"
        } else if (view == R.id.radio_load) {
            optionLink = Constant.LOAD_APP_URL
            Constant.SELECTEDFILENAME = "LOAD DOWNLOADED"
        } else if (view == R.id.radio_retrofit) {
            optionLink = Constant.RETROFIT_URL
            Constant.SELECTEDFILENAME = "RETROFIT DOWNLOADED"
        }
    }

}
