package com.udacity

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import androidx.core.content.withStyledAttributes
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0

    private val valueAnimator = ValueAnimator.ofInt(0,360).setDuration(2000)
    private var buttonBackgroundColor = 0
    private var buttonTextColor = 0
    private var buttonLoadingColor = 0
    private var buttonCircleColor = 0

     var buttonTextString = ""
    private var buttonProgress = 0
    private val painter = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 60.0f
        typeface = Typeface.create( "", Typeface.BOLD)
    }

    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->

        when (new) {
            ButtonState.Loading -> {
                buttonTextString = "Loading"
                valueAnimator.start()
            }
            ButtonState.Completed -> {
                buttonTextString = "Download"
                valueAnimator.cancel()
                buttonProgress = 0
            }
        }
        invalidate()

    }


    init {

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