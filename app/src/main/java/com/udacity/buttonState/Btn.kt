package com.udacity.buttonState

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import com.udacity.R
import com.udacity.constant.Constant
import kotlin.properties.Delegates

var textBtnLoad : String = "Loading"
var textBtnStop : String = "Download"
var angle : Float= 0.0F

private var valueAnimator: ValueAnimator = ValueAnimator.ofInt(0,360).setDuration(2000)

// initialize text and progress to make circle animated
private var buttonTextString : String = ""
private var buttonProgress : Int = 0
private var value : Int = 0
private  var widthSize : Float = 0.0F
private var heightSize : Float = 0.0F


class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    //initialize width and height of the button
//    private var valueAnimator = ValueAnimator()

    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { _, _, newIncome ->
//when i need the button animate
        if (newIncome == ButtonState.Loading ) {
            buttonTextString = textBtnLoad
            valueAnimator.start()
            invalidate()

//when i need it to stopped
        } else if (newIncome == ButtonState.Completed) {
            buttonTextString = textBtnStop
            valueAnimator.cancel()
            buttonProgress = 0
            invalidate()

        }
    }



    // override onMeasure function to sent width and height
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val sum : Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val minimumWidth: Int = sum
        val actualWidth: Int = resolveSizeAndState(minimumWidth, widthMeasureSpec, 0)
        val actualHeight: Int = resolveSizeAndState(MeasureSpec.getSize(actualWidth), heightMeasureSpec, 2
        )
        heightSize = actualHeight.toFloat()
        widthSize = actualWidth.toFloat()
        setMeasuredDimension(actualWidth, actualHeight)

    }

//override onDraw function to set dimensions the circle of animation
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    val doPainter = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL_AND_STROKE
        textAlign = Paint.Align.LEFT
        textSize = 63.0f
    }
    val buttonBackgroundColor =Paint(Paint.ANTI_ALIAS_FLAG).apply{
        color=context.getColor(R.color.colorPrimary)}
        canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), buttonBackgroundColor)
    val buttonLoadingColor =Paint(Paint.ANTI_ALIAS_FLAG).apply{
        color=context.getColor(R.color.white)}
        canvas?.drawRect(0f, 0f, widthSize * buttonProgress / 360f, heightSize.toFloat(), buttonLoadingColor)
    val buttonCircleColor =Paint(Paint.ANTI_ALIAS_FLAG).apply{
        color=context.getColor(R.color.colorAccent) }
        canvas?.drawArc(
            widthSize-150F, 40F,
           /* Constant.widthRight.toFloat()*/widthSize-100F,
            /*Constant.heighBottom.toFloat()*/140.0F,0f,
            buttonProgress.toFloat(), true, buttonCircleColor)

    canvas?.drawText(buttonTextString, widthSize / 3.0f, heightSize / 2.0f , doPainter)

    }

    init {

       buttonState = ButtonState.Completed

        valueAnimator.apply {
            addUpdateListener { valueAnimator ->
                buttonProgress = valueAnimator.animatedValue as Int
                angle = (value / 16).toFloat()
                invalidate() }
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
        }
        //initialize in context and apply animation
//        context.withStyledAttributes(attrs, R.styleable.LoadingButton) {
////            buttonBackgroundColor = getColor(R.styleable.LoadingButton_buttonBackgroundColor, 2)
//            buttonTextColor = getColor(R.styleable.LoadingButton_buttonTextColor, 2)
//            buttonLoadingColor = getColor(R.styleable.LoadingButton_buttonLoadingColor, 2)
//            buttonCircleColor = getColor(R.styleable.LoadingButton_buttonCircleAnimationColor, 2)
////        }
//        }

    }
}