package com.udacity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var widthSize = 0
    private var heightSize = 0
    private var circleProgress = 0f
    private var buttonProgress = 0f
    private var buttonText: String

    private var valueAnimator = ValueAnimator()

    private val background = ResourcesCompat.getColor(
        resources,
        R.color.colorPrimary,
        null
    )
    private val textColor = ResourcesCompat.getColor(
        resources,
        R.color.white,
        null
    )
    private val loadingButtonColor = ResourcesCompat.getColor(
        resources,
        R.color.colorPrimaryDark,
        null
    )

    private val paintButton = Paint().apply {
        isAntiAlias = true
        color = background
    }
    private  val paintText = Paint().apply {
        isAntiAlias = true
        color = textColor
        textSize = resources.getDimension(R.dimen.default_text_size)
    }
    private val paintButtonProgress = Paint().apply {
        isAntiAlias = true
        color = loadingButtonColor
    }
    private val paintCircle = Paint().apply {
        style = Paint.Style.FILL
        color = ContextCompat.getColor(context,R.color.colorAccent)
        strokeWidth = 3f
    }

    var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { _, _, new ->
        when (new) {
            ButtonState.Clicked -> {
            }
            ButtonState.Loading-> {
                buttonText = resources.getString(R.string.button_loading)
                valueAnimator = ValueAnimator.ofFloat(0f, measuredWidth.toFloat())
                valueAnimator.duration = 2650
                valueAnimator.addUpdateListener {
                    circleProgress = it.animatedValue as Float
                    buttonProgress = (widthSize.toFloat() / 500) * circleProgress
                    invalidate()
                }
                valueAnimator.addListener(object: AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        circleProgress = 0f
                        buttonProgress = 0f
                    }
                })
                valueAnimator.start()
                invalidate()
            }
            ButtonState.Completed -> {
                valueAnimator.cancel()
                buttonText = resources.getString(R.string.button_name)
                invalidate()
            }
        }
    }

    init {
        buttonText = resources.getString(R.string.button_name)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawButton(canvas)
        drawProgress(canvas)
        valueAnimator.repeatCount=ValueAnimator.INFINITE
    }

    private fun drawButton(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, widthSize.toFloat(), heightSize.toFloat(), paintButton)

        canvas?.drawText(
            buttonText,
            widthSize / 2 - paintText.measureText(buttonText) / 2,
            heightSize / 2 - (paintText.descent() + paintText.ascent()) / 2,
            paintText
        )
    }

    private fun drawProgress(canvas: Canvas?) {
        canvas?.drawRect(0f, 0f, buttonProgress, heightSize.toFloat(), paintButtonProgress)

        canvas?.drawArc(widthSize - 150f,
            heightSize / 2 - 35f,
            widthSize - 75f,
            heightSize / 2 + 35f,
            0F,
            circleProgress * 4,
            true,
            paintCircle)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
    }


}