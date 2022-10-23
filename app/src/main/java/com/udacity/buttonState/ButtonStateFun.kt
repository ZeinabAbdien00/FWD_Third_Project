package com.udacity.buttonState

import com.udacity.buttonState.ButtonState
import com.udacity.buttonState.LoadingButton
import kotlin.properties.Delegates
//variable to select state of button to make it animate or stopped
  lateinit var loadingBtn: LoadingButton
    var buttonStateFun: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
//when i need the button animate
        if (new == ButtonState.Loading) {
            loadingBtn.buttonTextString = "Loading"
            loadingBtn.valueAnimator.start()
            //when i need it to stopped
        } else if (new == ButtonState.Loading) {
            loadingBtn.buttonTextString = "Download"
            loadingBtn.valueAnimator.cancel()
            loadingBtn.buttonProgress = 0 }

    }
