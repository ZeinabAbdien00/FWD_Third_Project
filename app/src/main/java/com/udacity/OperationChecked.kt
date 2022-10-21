package com.udacity

var optionLink: String? = ""

fun operationChecked(view: Int) {

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
