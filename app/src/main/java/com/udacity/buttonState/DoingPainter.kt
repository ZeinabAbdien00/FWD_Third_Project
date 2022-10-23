package com.udacity.buttonState

import android.graphics.Paint
import android.graphics.Typeface

 val painter = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.FILL
    textAlign = Paint.Align.CENTER
    textSize = 60.0f
    typeface = Typeface.create( "", Typeface.BOLD_ITALIC)
}