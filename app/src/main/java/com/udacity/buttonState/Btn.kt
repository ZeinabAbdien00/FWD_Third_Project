package com.udacity.buttonState

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.udacity.R
import com.udacity.doPainter
//import com.udacity.painter
import com.udacity.textBtnLoad
import com.udacity.textBtnStop
import kotlin.properties.Delegates

// initialize text and progress to make circle animated
private var buttonTextString : String = ""
private var buttonProgress : Float = 0.0F

// passing animation from 0to 360 to make complete circle
private val valueAnimator: ValueAnimator = ValueAnimator.ofInt(0,360).setDuration(2000)
// initialize button color , circle color , loading color and text color
private var buttonBackgroundColor : Int = 0
private var buttonLoadingColor : Int= 0
private var buttonCircleColor : Int= 0
private var buttonTextColor : Int = 0


private  var widthSize : Float = 0.0F
private var heightSize : Float = 0.0F


class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    //initialize width and height of the button


    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { _, _, new ->
//when i need the button animate
        if (new == ButtonState.Loading) {
            buttonTextString = textBtnLoad
            valueAnimator.start()
//when i need it to stopped
        } else if (new == ButtonState.Loading) {
            buttonTextString = textBtnStop
            valueAnimator.cancel()
            buttonProgress = 0.0F
        }
        invalidate()
    }



    // override onMeasure function to sent width and height
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minimumWidth: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val actualWidth: Int = resolveSizeAndState(minimumWidth, widthMeasureSpec, 1)
        val actualHeight: Int = resolveSizeAndState(MeasureSpec.getSize(actualWidth), heightMeasureSpec, 2
        )
        widthSize = actualWidth.toFloat()
        heightSize = actualHeight.toFloat()
        setMeasuredDimension(actualWidth, actualHeight)

    }

//override onDraw function to set dimensions the circle of animation
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        doPainter.color = buttonBackgroundColor
        canvas?.drawRect(2f, 2f, widthSize.toFloat(), heightSize.toFloat(), doPainter)
        doPainter.color = buttonLoadingColor
        canvas?.drawRect(2f, 2f, widthSize * buttonProgress / 360f, heightSize.toFloat(), doPainter)
        doPainter.color = buttonCircleColor
        canvas?.drawArc(widthSize - 150f, 30f, widthSize - 180f, 120f,0f, buttonProgress.toFloat(), true, doPainter)
        doPainter.color = buttonTextColor
        canvas?.drawText(buttonTextString, widthSize / 2.0f, heightSize / 2.5f + 25.5f, doPainter)

    }

    init {

        buttonState = ButtonState.Completed

        valueAnimator.apply {
            addUpdateListener {
                buttonProgress = it.animatedValue as Float
            }
            invalidate()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
        }
        //initialize in context and apply animation
        context.withStyledAttributes(attrs , R.styleable.LoadingButton) {
            buttonBackgroundColor = getColor(R.styleable.LoadingButton_buttonBackgroundColor, 2)
            buttonTextColor = getColor(R.styleable.LoadingButton_buttonTextColor, 2)
            buttonLoadingColor = getColor(R.styleable.LoadingButton_buttonLoadingColor, 2)
            buttonCircleColor = getColor(R.styleable.LoadingButton_buttonCircleAnimationColor, 2)
        }
    }

}