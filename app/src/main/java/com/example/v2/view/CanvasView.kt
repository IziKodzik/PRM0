package com.example.v2.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.v2.shared.Shared
import java.time.LocalDate
import kotlin.math.abs

class CanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var w = 0f
    private var h = 0f
    var graphDate: LocalDate = LocalDate.now()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.w = w.toFloat()
        this.h = h.toFloat()
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {

        if (graphDate.monthValue < 0 && graphDate.year < 0)
            return

        val date = LocalDate.of(graphDate.year, graphDate.month, 1)
        var daysCount = date.month.maxLength()
        if (date.monthValue == 2 && graphDate.year % 4 != 0)
            daysCount--

        val systemBeginX = 75f
        val systemBeginY = h - 60
        val systemEndX = w - 60
        val systemEndY = 150f

        val paint = Paint()
        paint.strokeWidth = 10f

        canvas?.drawLine(systemBeginX, systemBeginY, systemEndX + 30, systemBeginY, paint) //horizontal
        canvas?.drawLine(systemBeginX + 5, systemBeginY, systemBeginX + 5,//vertical
            systemEndY - 30, paint)

        paint.strokeWidth = 1f
        paint.textSize = 30f

        for (i in daysCount downTo 1){
            canvas?.drawText(i.toString(), systemBeginX - 50f,
                systemBeginY + 10 -
                        ((normalize(i.toDouble(), 1.0,daysCount.toDouble())).toFloat()
                                * (systemBeginY-systemEndY)),
                paint)
        }

        val daysBalance = DoubleArray(daysCount) { 0.0 }
        Shared.transferList.filter { it.date.month == date.month && it.date.year == date.year }
            .onEach {
                daysBalance[it.date.dayOfMonth - 1] += it.amount
            }

        var current = 0.0
        var tmpMaxValue: Double? = null
        var tmpMinValue: Double? = null
        daysBalance.forEach {
            current += it
            if (tmpMaxValue == null || current > tmpMaxValue?: Double.MIN_VALUE)
                tmpMaxValue = current
            if (tmpMinValue == null || current < tmpMinValue?: Double.MAX_VALUE)
                tmpMinValue = current
        }
        val minValue:Double = tmpMinValue!!
        var maxValue:Double = tmpMaxValue!!
        if(maxValue == 0.0 && minValue == 0.0)
            maxValue = 0.00000000001


        paint.color = Color.GRAY
        if (minValue <= 0 && maxValue >= 0) {
            paint.strokeWidth = 4f
            val zeroPoint = normalize(0.0,minValue,maxValue).toFloat() * (systemEndX - systemBeginX)
            canvas?.drawLine(systemBeginX + zeroPoint, systemEndY - 30, systemBeginX + zeroPoint, systemBeginY + 15, paint)
        }
        var minX = 0f
        if (abs(minValue) < 1000) {
            minX = 45f
        } else if (abs(minValue) < 100000) {
            minX = 25f
        }

        val maxX: Float = when {
            abs(maxValue) < 100 -> {
                95f
            }
            abs(maxValue) < 1000 -> {
                115f
            }
            abs(maxValue) < 100000 -> {
                140f
            }
            else -> {
                160f
            }
        }
        canvas?.drawText(Shared.formatNumber(minValue), minX, systemBeginY + 60, paint)
        canvas?.drawText(Shared.formatNumber(maxValue), w - maxX, systemBeginY + 60, paint)

        current = 0.0
        paint.strokeWidth = 7f
        daysBalance.forEachIndexed { index, value ->
            val tmpCurrent = current
            current += value
            when {
                current < 0 -> paint.color = Color.RED
                current > 0 -> paint.color = Color.parseColor("#0dbf49")
                else -> paint.color = Color.BLACK
            }
            if(index != 0){
                canvas?.drawLine(
                    systemBeginX + 5 + normalize(tmpCurrent, minValue, maxValue).toFloat() * (systemEndX-systemBeginX),
                    systemBeginY + (1-normalize(index.toDouble(),1.0, daysCount.toDouble()).toFloat()
                            * (systemBeginY - systemEndY)) ,
                    systemBeginX + 5 + normalize(current, minValue, maxValue).toFloat() * (systemEndX-systemBeginX),
                    systemBeginY + (1-normalize(index.toDouble() + 1,1.0, daysCount.toDouble()).toFloat() * (systemBeginY - systemEndY)) ,
                    paint
                )
            }
            canvas?.drawCircle(
                systemBeginX + 5 + normalize(current, minValue, maxValue).toFloat() * (systemEndX-systemBeginX),
                systemBeginY + (1-normalize(index.toDouble() + 1,1.0, daysCount.toDouble()).toFloat() * (systemBeginY - systemEndY)) ,
                10f, paint
            )
        }

    }

    private fun normalize(
        tmpCurrent: Double,
        minValue: Double,
        maxValue: Double
    ) = ((tmpCurrent + (-minValue)) / (-minValue + maxValue))
}
