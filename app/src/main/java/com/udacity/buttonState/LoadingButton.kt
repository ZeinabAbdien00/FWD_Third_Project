package com.udacity.buttonState

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.udacity.R
import com.udacity.painter
import com.udacity.textBtnLoad
import com.udacity.textBtnStop
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    //initialize width and height of the button
    private var widthSize = 0
    private var heightSize = 0
// passing animation from 0to 360 to make complete circle
private val valueAnimator: ValueAnimator = ValueAnimator.ofInt(0,360).setDuration(2000)
    // initialize button color , circle color , loading color and text color
    private var buttonBackgroundColor = 0
    private var buttonTextColor = 0
    private var buttonLoadingColor = 0
    private var buttonCircleColor = 0
// initialize text and progress to make circle animated
    var buttonTextString = ""
    var buttonProgress = 0

    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
//when i need the button animate
        if (new == ButtonState.Loading) {
            buttonTextString = textBtnLoad
            valueAnimator.start()
//when i need it to stopped
        } else if (new == ButtonState.Loading) {
            buttonTextString = textBtnStop
            valueAnimator.cancel()
            buttonProgress = 0
        }
        invalidate()
    }


    init {
        //initialize in context and apply animation

        context.withStyledAttributes(attrs , R.styleable.LoadingButton) {
            buttonBackgroundColor = getColor(R.styleable.LoadingButton_buttonBackgroundColor, 0)
            buttonTextColor = getColor(R.styleable.LoadingButton_buttonTextColor, 0)
            buttonLoadingColor = getColor(R.styleable.LoadingButton_buttonLoadingColor, 0)
            buttonCircleColor = getColor(R.styleable.LoadingButton_buttonCircleAnimationColor, 0)
        }
        valueAnimator.apply {
            addUpdateListener {
                buttonProgress = it.animatedValue as Int
                invalidate()
            }
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
        }
        buttonState = ButtonState.Completed
    }

//override onDraw function to set dimensions the circle of animation
    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)
        painter.color = buttonBackgroundColor
        canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), painter)
        painter.color = buttonLoadingColor
        canvas?.drawRect(0f, 0f, widthSize * buttonProgress / 360f, heightSize.toFloat(), painter)
        painter.color = buttonCircleColor
        canvas?.drawArc(widthSize - 250f, 40f, widthSize - 200f, 100f,0f, buttonProgress.toFloat(), true, painter)
        painter.color = buttonTextColor
        canvas?.drawText(buttonTextString, widthSize / 2.0f, heightSize / 2.0f + 25.0f, painter)

    }
// override onMeasure function to sent width and height
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minimumWidth: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val actualWidth: Int = resolveSizeAndState(minimumWidth, widthMeasureSpec, 1)
        val actualHeight: Int = resolveSizeAndState(
            MeasureSpec.getSize(actualWidth),
            heightMeasureSpec,
            0
        )
        widthSize = actualWidth
        heightSize = actualHeight
        setMeasuredDimension(actualWidth, actualHeight)

    }

}